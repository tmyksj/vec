package vec.domain.repository

import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono
import vec.domain.entity.Clerk

@NoRepositoryBean
interface ClerkRepository : ReactiveCrudRepository<Clerk, String> {

    /**
     * 店員を返します。
     * @param userId ユーザ ID
     * @return　店舗
     */
    fun countByUserId(userId: String): Mono<Long>

    /**
     * 店員を返します。
     * @param storeId 店舗 ID
     * @param userId ユーザ ID
     * @return　店舗
     */
    fun findByStoreIdAndUserId(storeId: String, userId: String): Mono<Clerk>

}
