package vec.domain.repository

import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono
import vec.domain.entity.Cart

@NoRepositoryBean
interface CartRepository : ReactiveCrudRepository<Cart, String> {

    /**
     * ユーザのカートを返します。
     * @param userId ユーザ ID
     * @return カート
     */
    fun findByUserId(userId: String): Mono<Cart>

}
