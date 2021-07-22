package vec.domain.repository

import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import vec.domain.entity.CartProduct

@NoRepositoryBean
interface CartProductRepository : ReactiveCrudRepository<CartProduct, String> {

    /**
     * カートにある商品を返します。
     * @param cartId カート ID
     * @return カートにある商品
     */
    fun findAllByCartId(cartId: String): Flux<CartProduct>

}
