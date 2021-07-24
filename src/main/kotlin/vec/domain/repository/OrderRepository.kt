package vec.domain.repository

import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import vec.domain.entity.Order

@NoRepositoryBean
interface OrderRepository : ReactiveCrudRepository<Order, String> {

    fun findAllByUserId(userId: String): Flux<Order>

}
