package vec.useCase.query.impl

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import vec.domain.entity.User
import vec.domain.repository.OrderRepository
import vec.useCase.dto.OrderDto
import vec.useCase.query.GetOrderListQuery

@Component
@Transactional
class GetOrderListQueryImpl(
    private val orderRepository: OrderRepository,
) : GetOrderListQuery {

    override fun perform(
        principal: User,
    ): Mono<Flux<OrderDto>> {
        return Mono.fromCallable {
            orderRepository.findAllByUserId(principal.id)
                .map {
                    OrderDto(
                        id = it.id,
                        userId = it.userId,
                        amount = it.amount,
                        tax = it.tax,
                        total = it.total,
                        orderedDate = it.orderedDate,
                    )
                }
        }
    }

}
