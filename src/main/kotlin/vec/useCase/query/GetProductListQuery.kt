package vec.useCase.query

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import vec.domain.entity.User
import vec.useCase.dto.ProductDto

/**
 * 商品のリストを取得します。
 */
interface GetProductListQuery {

    /**
     * @param principal principal
     * @return 商品のリスト
     */
    fun perform(
        principal: User?,
    ): Mono<Flux<ProductDto>>

}
