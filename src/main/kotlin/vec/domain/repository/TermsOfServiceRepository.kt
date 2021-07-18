package vec.domain.repository

import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import vec.domain.entity.TermsOfService

@Component
@Transactional
interface TermsOfServiceRepository : ReactiveCrudRepository<TermsOfService, String> {

    @Query(
        value = """
            select *
            from terms_of_service
            where applied_date <= now()
            order by applied_date desc
        """,
    )
    fun findCurrentVersion(): Mono<TermsOfService>

}
