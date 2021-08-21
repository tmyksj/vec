package vec.infrastructure.repository.impl

import org.springframework.data.r2dbc.repository.Query
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import vec.domain.entity.TermsOfService
import vec.domain.repository.TermsOfServiceRepository

@Component
@Transactional
interface TermsOfServiceRepositoryImpl : TermsOfServiceRepository {

    @Query(
        value = """
            select *
            from terms_of_service
            where applied_date <= now()
            order by applied_date desc
        """,
    )
    override fun findCurrentVersion(): Mono<TermsOfService>

}
