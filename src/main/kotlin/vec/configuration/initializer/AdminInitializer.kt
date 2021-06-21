package vec.configuration.initializer

import org.slf4j.Logger
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
import org.springframework.security.crypto.password.PasswordEncoder
import reactor.core.publisher.Mono
import vec.domain.entity.User
import vec.domain.repository.UserRepository
import java.util.*
import javax.annotation.PostConstruct

@Configuration
@DependsOn(value = ["flyway"])
class AdminInitializer(
    private val logger: Logger,
    private val passwordEncoder: PasswordEncoder,
    private val userRepository: UserRepository,
) {

    @PostConstruct
    fun initialize() {
        userRepository.existsByHasAuthorityAdmin(true)
            .filter { !it }
            .flatMap {
                val passwordRaw: String = UUID.randomUUID().toString()

                Mono.zip(
                    userRepository.save(
                        User(
                            email = "admin@vec",
                            passwordEncrypted = passwordEncoder.encode(passwordRaw),
                            isAccountExpired = false,
                            isAccountLocked = false,
                            isCredentialsExpired = false,
                            isEnabled = true,
                            hasAuthorityAdmin = true,
                            hasAuthorityConsumer = false,
                        )
                    ),
                    Mono.just(passwordRaw),
                )
            }.doOnNext {
                logger.info("Created admin user, email: ${it.t1.email}, password: ${it.t2}")
            }.subscribe()
    }

}
