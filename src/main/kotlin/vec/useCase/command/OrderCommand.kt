package vec.useCase.command

import reactor.core.publisher.Mono
import vec.domain.entity.User
import vec.useCase.dto.OrderDto

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
    ): Mono<OrderDto>

    class CartIsEmptyException : RuntimeException()

    class ProductIsNotFoundException : RuntimeException()

}
