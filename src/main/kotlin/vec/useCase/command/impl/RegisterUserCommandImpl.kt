package vec.useCase.command.impl

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import vec.domain.entity.User
import vec.domain.repository.UserRepository
import vec.useCase.command.RegisterUserCommand

@Component
@Transactional
class RegisterUserCommandImpl(
    val passwordEncoder: PasswordEncoder,
    val userRepository: UserRepository,
) : RegisterUserCommand {

    override fun perform(
        request: RegisterUserCommand.Request,
    ): Mono<RegisterUserCommand.Response> {
        return userRepository.findByEmail(request.email)
            .doOnNext {
                throw RegisterUserCommand.AlreadyInUseException()
            }.switchIfEmpty {
                userRepository.save(
                    User(
                        email = request.email,
                        passwordEncrypted = passwordEncoder.encode(request.passwordRaw),
                        isAccountExpired = false,
                        isAccountLocked = false,
                        isCredentialsExpired = false,
                        isEnabled = true,
                        hasAuthorityAdmin = request.hasAuthorityAdmin,
                        hasAuthorityGeneral = request.hasAuthorityGeneral,
                    )
                )
            }.map {
                RegisterUserCommand.Response()
            }
    }

}
