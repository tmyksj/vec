package vec.domain.repository

import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import vec.domain.entity.OrderProduct

@NoRepositoryBean
interface OrderProductRepository : ReactiveCrudRepository<OrderProduct, String> {

    /**
     * 注文商品を返します。
     * @param orderId 注文 ID
     * @return 注文商品
     */
    fun findAllByOrderId(orderId: String): Flux<OrderProduct>

}
