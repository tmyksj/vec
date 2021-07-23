package vec.useCase.command.impl

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.onErrorMap
import vec.domain.entity.User
import vec.domain.service.AccountService
import vec.useCase.command.ModifyUserPasswordCommand

@Component
@Transactional
class ModifyUserPasswordCommandImpl(
    private val accountService: AccountService,
) : ModifyUserPasswordCommand {

    override fun perform(
        principal: User,
        currentPasswordRaw: String,
        newPasswordRaw: String,
    ): Mono<User> {
        return Mono.defer {
            accountService.modifyPassword(principal, currentPasswordRaw, newPasswordRaw)
        }.onErrorMap(AccountService.PasswordMustMatchException::class) {
            ModifyUserPasswordCommand.PasswordMismatchesException()
        }
    }

}
