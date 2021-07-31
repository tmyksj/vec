package vec.useCase.query.impl

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import vec.domain.entity.User
import vec.domain.repository.OrderProductRepository
import vec.domain.repository.OrderRepository
import vec.useCase.dto.OrderDto
import vec.useCase.dto.OrderProductDto
import vec.useCase.query.GetOrderQuery

@Component
@Transactional
class GetOrderQueryImpl(
    private val orderProductRepository: OrderProductRepository,
    private val orderRepository: OrderRepository,
) : GetOrderQuery {

    override fun perform(
        principal: User,
        id: String,
    ): Flux<Any> {
        return Mono.defer {
            orderRepository.findByIdAndUserId(id, principal.id)
        }.switchIfEmpty {
            throw GetOrderQuery.OrderIsNotFoundException()
        }.flatMapMany { order ->
            Flux.merge(
                Mono.just(
                    OrderDto(
                        id = order.id,
                        userId = order.userId,
                        amount = order.amount,
                        tax = order.tax,
                        total = order.total,
                        orderedDate = order.orderedDate,
                    )
                ),
                orderProductRepository.findAllByOrderId(order.id)
                    .map {
                        OrderProductDto(
                            id = it.id,
                            orderId = it.orderId,
                            productId = it.productId,
                            productName = it.productName,
                            productDescription = it.productDescription,
                            productAmount = it.productAmount,
                            productTaxRate = it.productTaxRate,
                            productTax = it.productTax,
                            productTotal = it.productTotal,
                            quantity = it.quantity,
                            amount = it.amount,
                            tax = it.tax,
                            total = it.total,
                        )
                    },
            )
        }
    }

}
