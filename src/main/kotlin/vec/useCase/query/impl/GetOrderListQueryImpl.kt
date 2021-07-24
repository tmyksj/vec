package vec.useCase.query.impl

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import vec.domain.entity.Order
import vec.domain.entity.User
import vec.domain.repository.OrderRepository
import vec.useCase.query.GetOrderListQuery

@Component
@Transactional
class GetOrderListQueryImpl(
    private val orderRepository: OrderRepository,
) : GetOrderListQuery {

    override fun perform(
        principal: User,
    ): Flux<Order> {
        return Flux.defer {
            orderRepository.findAllByUserId(principal.id)
        }
    }

}
