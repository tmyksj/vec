package vec.domain.repository

import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono
import vec.domain.entity.StoreAgreement

@NoRepositoryBean
interface StoreAgreementRepository : ReactiveCrudRepository<StoreAgreement, String> {

    /**
     * 現時点で効力を持つ店舗利用同意文書を返します。
     * @return 店舗利用同意文書
     */
    fun findCurrentVersion(): Mono<StoreAgreement>

}
