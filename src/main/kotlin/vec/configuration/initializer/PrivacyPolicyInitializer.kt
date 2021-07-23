package vec.configuration.initializer

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
import reactor.core.publisher.Mono
import vec.domain.entity.PrivacyPolicy
import vec.domain.repository.PrivacyPolicyRepository
import java.time.LocalDateTime
import javax.annotation.PostConstruct

@Configuration
@DependsOn(value = ["flyway"])
class PrivacyPolicyInitializer(
    private val privacyPolicyRepository: PrivacyPolicyRepository,
) {

    @PostConstruct
    fun initialize() {
        Mono.defer {
            privacyPolicyRepository.count()
        }.filter {
            it == 0L
        }.flatMap {
            privacyPolicyRepository.save(
                PrivacyPolicy(
                    body = """
                        <h2 style="font-size: 1.25rem; line-height: 1.75rem;">Privacy Policy</h2>
                        <p style="margin-bottom: 2rem; margin-top: 2rem;">
                            Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor 
                            incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud 
                            exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure 
                            dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. 
                            Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt 
                            mollit anim id est laborum.
                        </p>
                    """.trimIndent(),
                    appliedDate = LocalDateTime.now(),
                )
            )
        }.subscribe()
    }

}
