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

    override fun modifyEmail(
        user: User,
        email: String,
    ): Mono<User> {
        return Mono.fromCallable {
            user.modifyEmail(email)
        }.flatMap {
            hasUniqueEmail(it)
        }.flatMap {
            userRepository.save(it)
        }
    }

    override fun modifyPassword(
        user: User,
        currentPasswordRaw: String,
        newPasswordRaw: String,
    ): Mono<User> {
        return Mono.fromCallable {
            if (passwordEncoder.matches(currentPasswordRaw, user.passwordEncrypted)) {
                user.modifyPasswordEncrypted(passwordEncoder.encode(newPasswordRaw))
            } else {
                throw AccountService.PasswordMustMatchException()
            }
        }.flatMap {
            userRepository.save(it)
        }
    }

    override fun register(
        email: String,
        passwordRaw: String,
        hasRoleAdmin: Boolean,
        hasRoleClerk: Boolean,
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
                hasRoleClerk = hasRoleClerk,
                hasRoleConsumer = hasRoleConsumer,
            )
        }.flatMap {
            hasUniqueEmail(it)
        }.flatMap {
            userRepository.save(it)
        }
    }

    override fun unregister(user: User): Mono<User> {
        return Mono.fromCallable {
            user.modifyToDisabled()
        }.flatMap {
            userRepository.save(it)
        }
    }

    private fun hasUniqueEmail(user: User): Mono<User> {
        return Mono.defer {
            userRepository.findByEmail(user.email)
        }.switchIfEmpty {
            Mono.just(user)
        }.map {
            if (user.id == it.id) {
                user
            } else {
                throw AccountService.EmailMustBeUniqueException()
            }
        }
    }

}
