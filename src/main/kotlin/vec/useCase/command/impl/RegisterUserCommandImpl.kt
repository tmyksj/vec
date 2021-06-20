package vec.useCase.command.impl

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import vec.domain.service.AccountService
import vec.useCase.command.RegisterUserCommand

@Component
@Transactional
class RegisterUserCommandImpl(
    private val accountService: AccountService,
) : RegisterUserCommand {

    override fun perform(
        request: RegisterUserCommand.Request,
    ): Mono<RegisterUserCommand.Response> {
        return Mono.defer {
            accountService.register(
                email = request.email,
                passwordRaw = request.passwordRaw,
                hasAuthorityAdmin = request.hasAuthorityAdmin,
                hasAuthorityGeneral = request.hasAuthorityGeneral,
            )
        }.switchIfEmpty {
            throw RegisterUserCommand.AlreadyInUseException()
        }.map {
            RegisterUserCommand.Response()
        }
    }

}
