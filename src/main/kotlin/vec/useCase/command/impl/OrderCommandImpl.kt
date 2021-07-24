package vec.useCase.command.impl

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.onErrorMap
import vec.domain.entity.Order
import vec.domain.entity.User
import vec.domain.service.ECommerceOrderService
import vec.useCase.command.OrderCommand

@Component
@Transactional
class OrderCommandImpl(
    private val eCommerceOrderService: ECommerceOrderService,
) : OrderCommand {

    override fun perform(principal: User): Mono<Order> {
        return Mono.defer {
            eCommerceOrderService.order(principal)
        }.onErrorMap(ECommerceOrderService.CartProductMustExistException::class) {
            OrderCommand.CartIsEmptyException()
        }.onErrorMap(ECommerceOrderService.ProductMustExistException::class) {
            OrderCommand.ProductIsNotFoundException()
        }
    }

}
