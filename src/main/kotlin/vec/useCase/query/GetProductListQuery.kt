package vec.useCase.query

import reactor.core.publisher.Flux
import vec.domain.entity.Product
import vec.domain.entity.User

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
    ): Flux<Product>

}
