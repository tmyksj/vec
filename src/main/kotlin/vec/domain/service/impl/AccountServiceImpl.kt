package vec.domain.service.impl

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import vec.domain.entity.User
import vec.domain.repository.UserRepository
import vec.domain.service.AccountService

@Component
@Transactional
class AccountServiceImpl(
    private val passwordEncoder: PasswordEncoder,
    private val userRepository: UserRepository,
) : AccountService {

    override fun isValid(user: User): Mono<User> {
        return Mono.defer {
            userRepository.findByEmail(user.email)
        }.switchIfEmpty {
            Mono.just(user)
        }.mapNotNull {
            if (user.id == it.id) {
                user
            } else {
                null
            }
        }
    }

    override fun register(
        email: String,
        passwordRaw: String,
        hasRoleAdmin: Boolean,
        hasRoleConsumer: Boolean
    ): Mono<User> {
        return Mono.fromCallable {
            User(
                email = email,
                passwordEncrypted = passwordEncoder.encode(passwordRaw),
                isAccountExpired = false,
                isAccountLocked = false,
                isCredentialsExpired = false,
                isEnabled = true,
                hasRoleAdmin = hasRoleAdmin,
                hasRoleConsumer = hasRoleConsumer,
            )
        }.flatMap {
            isValid(it)
        }.flatMap {
            userRepository.save(it)
        }
    }

}
