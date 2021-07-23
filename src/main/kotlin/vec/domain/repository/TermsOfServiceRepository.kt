package vec.domain.repository

import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono
import vec.domain.entity.TermsOfService

@NoRepositoryBean
interface TermsOfServiceRepository : ReactiveCrudRepository<TermsOfService, String> {

    /**
     * 現時点で効力を持つ利用規約を返します。
     * @return 利用規約
     */
    fun findCurrentVersion(): Mono<TermsOfService>

}
