package vec.useCase.command.impl

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.onErrorMap
import vec.domain.entity.User
import vec.domain.service.AccountService
import vec.useCase.command.ModifyUserEmailCommand

@Component
@Transactional
class ModifyUserEmailCommandImpl(
    private val accountService: AccountService,
) : ModifyUserEmailCommand {

    override fun perform(
        principal: User,
        email: String,
    ): Mono<User> {
        return Mono.defer {
            accountService.modifyEmail(principal, email)
        }.onErrorMap(AccountService.EmailMustBeUniqueException::class) {
            ModifyUserEmailCommand.EmailIsAlreadyInUseException()
        }
    }

}
