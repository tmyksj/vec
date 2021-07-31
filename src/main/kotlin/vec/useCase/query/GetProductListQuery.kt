package vec.useCase.query

import reactor.core.publisher.Flux
import vec.domain.entity.User
import vec.useCase.dto.ProductDto

/**
 * 注目の商品のリストを取得します。
 */
interface GetProductListQuery {

    /**
     * @param principal principal
     * @return 注目の商品のリスト
     */
    fun perform(
        principal: User?,
    ): Flux<ProductDto>

}
