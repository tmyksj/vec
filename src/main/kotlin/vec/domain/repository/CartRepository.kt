package vec.domain.repository

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import vec.domain.entity.Cart

@Component
@Transactional
interface CartRepository : ReactiveCrudRepository<Cart, String> {

    fun findByUserId(userId: String): Mono<Cart>

}
