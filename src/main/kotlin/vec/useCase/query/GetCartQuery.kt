package vec.useCase.query

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import vec.domain.entity.User
import vec.useCase.dto.CartDto
import vec.useCase.dto.CartProductDto

/**
 * カートを取得します。
 * 追加日時の降順です。
 */
interface GetCartQuery {

    /**
     * @param principal principal
     * @return [CartDto], [CartProductDto]
     */
    fun perform(
        principal: User,
    ): Mono<Flux<Any>>

}
