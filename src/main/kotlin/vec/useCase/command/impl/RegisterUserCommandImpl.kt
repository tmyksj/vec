package vec.useCase.command.impl

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.onErrorMap
import vec.domain.entity.User
import vec.domain.service.AccountService
import vec.useCase.command.RegisterUserCommand

@Component
@Transactional
class RegisterUserCommandImpl(
    private val accountService: AccountService,
) : RegisterUserCommand {

    override fun perform(
        principal: User?,
        email: String,
        passwordRaw: String,
        hasRoleAdmin: Boolean,
        hasRoleConsumer: Boolean,
    ): Mono<User> {
        return Mono.defer {
            accountService.register(
                email = email,
                passwordRaw = passwordRaw,
                hasRoleAdmin = hasRoleAdmin,
                hasRoleConsumer = hasRoleConsumer,
            )
        }.onErrorMap(AccountService.EmailMustBeUniqueException::class) {
            RegisterUserCommand.EmailIsAlreadyInUseException()
        }
    }

}
