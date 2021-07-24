package vec.useCase.command

import reactor.core.publisher.Mono
import vec.domain.entity.Order
import vec.domain.entity.User

/**
 * カートの商品を注文します。
 */
interface OrderCommand {

    /**
     * @param principal principal
     * @return 注文
     */
    fun perform(
        principal: User,
    ): Mono<Order>

    class CartIsEmptyException : RuntimeException()

    class ProductIsNotFoundException : RuntimeException()

}
