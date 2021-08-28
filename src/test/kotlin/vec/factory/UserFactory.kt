package vec.factory

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestComponent
import org.springframework.security.crypto.password.PasswordEncoder
import vec.domain.entity.User
import vec.domain.repository.UserRepository
import java.util.*

@TestComponent
class UserFactory {

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    private lateinit var userRepository: UserRepository

    fun create(
        email: String = "${UUID.randomUUID()}@example.com",
        passwordEncrypted: String = passwordEncoder.encode("password"),
        isAccountExpired: Boolean = false,
        isAccountLocked: Boolean = false,
        isCredentialsExpired: Boolean = false,
        isEnabled: Boolean = true,
        hasRoleAdmin: Boolean = false,
        hasRoleClerk: Boolean = false,
        hasRoleConsumer: Boolean = false,
    ): User {
        return checkNotNull(
            userRepository.save(
                User(
                    email = email,
                    passwordEncrypted = passwordEncrypted,
                    isAccountExpired = isAccountExpired,
                    isAccountLocked = isAccountLocked,
                    isCredentialsExpired = isCredentialsExpired,
                    isEnabled = isEnabled,
                    hasRoleAdmin = hasRoleAdmin,
                    hasRoleClerk = hasRoleClerk,
                    hasRoleConsumer = hasRoleConsumer,
                )
            ).block()
        )
    }

}
