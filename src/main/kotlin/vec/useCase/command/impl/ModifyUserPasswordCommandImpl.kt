package vec.useCase.command.impl

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.onErrorMap
import vec.domain.service.AccountService
import vec.useCase.command.ModifyUserPasswordCommand

@Component
@Transactional
class ModifyUserPasswordCommandImpl(
    private val accountService: AccountService,
) : ModifyUserPasswordCommand {

    override fun perform(
        request: ModifyUserPasswordCommand.Request,
    ): Mono<ModifyUserPasswordCommand.Response> {
        return Mono.defer {
            accountService.modifyPassword(request.principal, request.currentPasswordRaw, request.newPasswordRaw)
        }.onErrorMap(AccountService.PasswordMustMatchException::class) {
            throw ModifyUserPasswordCommand.PasswordMismatchesException()
        }.map {
            ModifyUserPasswordCommand.Response()
        }
    }

}
