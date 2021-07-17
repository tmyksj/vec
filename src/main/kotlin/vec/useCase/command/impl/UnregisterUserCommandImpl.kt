package vec.useCase.command.impl

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import vec.domain.service.AccountService
import vec.useCase.command.UnregisterUserCommand

@Component
@Transactional
class UnregisterUserCommandImpl(
    private val accountService: AccountService,
) : UnregisterUserCommand {

    override fun perform(
        request: UnregisterUserCommand.Request,
    ): Mono<UnregisterUserCommand.Response> {
        return Mono.defer {
            accountService.unregister(request.principal)
        }.map {
            UnregisterUserCommand.Response()
        }
    }

}
