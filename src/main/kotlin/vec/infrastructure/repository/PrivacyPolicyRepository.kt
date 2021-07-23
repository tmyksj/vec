package vec.infrastructure.repository

import org.springframework.data.r2dbc.repository.Query
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import vec.domain.entity.PrivacyPolicy
import vec.domain.repository.PrivacyPolicyRepository

@Component
@Transactional
interface PrivacyPolicyRepository : PrivacyPolicyRepository {

    @Query(
        value = """
            select *
            from privacy_policy
            where applied_date <= now()
            order by applied_date desc
        """,
    )
    override fun findCurrentVersion(): Mono<PrivacyPolicy>

}
