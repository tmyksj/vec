package vec.domain.repository

import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import vec.domain.entity.Order

@NoRepositoryBean
interface OrderRepository : ReactiveCrudRepository<Order, String> {

    /**
     * ユーザの注文を返します。
     * @param userId ユーザ ID
     * @return 注文
     */
    fun findAllByUserId(userId: String): Flux<Order>

}
