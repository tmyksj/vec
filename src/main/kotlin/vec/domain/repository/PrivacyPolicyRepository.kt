package vec.domain.repository

import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import vec.domain.entity.PrivacyPolicy

@Component
@Transactional
interface PrivacyPolicyRepository : ReactiveCrudRepository<PrivacyPolicy, String> {

    @Query(
        value = """
            select *
            from privacy_policy
            where applied_date <= now()
            order by applied_date desc
        """,
    )
    fun findCurrentVersion(): Mono<PrivacyPolicy>

}
