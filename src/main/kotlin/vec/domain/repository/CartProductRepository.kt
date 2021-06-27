package vec.domain.repository

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import vec.domain.entity.CartProduct

@Component
@Transactional
interface CartProductRepository : ReactiveCrudRepository<CartProduct, String> {

    fun findAllByCartId(cartId: String): Flux<CartProduct>

}
