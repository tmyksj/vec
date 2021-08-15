package vec.infrastructure.repository

import org.springframework.data.r2dbc.repository.Query
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import vec.domain.entity.StoreAgreement
import vec.domain.repository.StoreAgreementRepository

@Component
@Transactional
interface StoreAgreementRepository : StoreAgreementRepository {

    @Query(
        value = """
            select *
            from store_agreement
            where applied_date <= now()
            order by applied_date desc
        """,
    )
    override fun findCurrentVersion(): Mono<StoreAgreement>

}
