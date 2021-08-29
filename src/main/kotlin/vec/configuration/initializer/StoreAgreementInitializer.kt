package vec.configuration.initializer

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
import reactor.core.publisher.Mono
import vec.domain.entity.StoreAgreement
import vec.domain.repository.StoreAgreementRepository
import java.time.LocalDateTime
import javax.annotation.PostConstruct

@Configuration
@DependsOn(value = ["flyway"])
class StoreAgreementInitializer(
    private val storeAgreementRepository: StoreAgreementRepository,
) {

    @PostConstruct
    fun initialize() {
        Mono.defer {
            storeAgreementRepository.count()
        }.filter {
            it == 0L
        }.flatMap {
            storeAgreementRepository.save(
                StoreAgreement(
                    body = """
                        <h2 style="font-size: 1.25rem; line-height: 1.75rem;">Store Agreement</h2>
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
