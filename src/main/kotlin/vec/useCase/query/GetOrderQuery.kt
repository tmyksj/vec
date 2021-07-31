package vec.useCase.query

import reactor.core.publisher.Flux
import vec.domain.entity.User
import vec.useCase.dto.OrderDto
import vec.useCase.dto.OrderProductDto

/**
 * 注文を返します。
 */
interface GetOrderQuery {

    /**
     * @param principal principal
     * @param id 注文 ID
     * @return 注文 [OrderDto], [OrderProductDto]
     */
    fun perform(
        principal: User,
        id: String,
    ): Flux<Any>

    class OrderIsNotFoundException : RuntimeException()

}
