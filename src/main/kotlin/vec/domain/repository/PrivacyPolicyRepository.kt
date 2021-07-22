package vec.domain.repository

import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono
import vec.domain.entity.PrivacyPolicy

@NoRepositoryBean
interface PrivacyPolicyRepository : ReactiveCrudRepository<PrivacyPolicy, String> {

    /**
     * 現時点で効力を持つプライバシーポリシーを返します。
     * @return プライバシーポリシー
     */
    fun findCurrentVersion(): Mono<PrivacyPolicy>

}
