package vec.useCase.query

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import vec.domain.entity.User
import vec.useCase.dto.OrderDto

/**
 * 注文のリストを返します。
 */
interface GetOrderListQuery {

    /**
     * @param principal principal
     * @return 注文のリスト
     */
    fun perform(
        principal: User,
    ): Mono<Flux<OrderDto>>

}
