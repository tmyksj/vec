package vec.useCase.command.impl

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.onErrorMap
import vec.domain.service.AccountService
import vec.useCase.command.ModifyUserEmailCommand

@Component
@Transactional
class ModifyUserEmailCommandImpl(
    private val accountService: AccountService,
) : ModifyUserEmailCommand {

    override fun perform(
        request: ModifyUserEmailCommand.Request,
    ): Mono<ModifyUserEmailCommand.Response> {
        return Mono.defer {
            accountService.modifyEmail(request.principal, request.email)
        }.onErrorMap(AccountService.EmailMustBeUniqueException::class) {
            throw ModifyUserEmailCommand.EmailIsAlreadyInUseException()
        }.map {
            ModifyUserEmailCommand.Response()
        }
    }

}
