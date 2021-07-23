package vec.useCase.query

import reactor.core.publisher.Mono
import vec.domain.entity.Product
import vec.domain.entity.User

/**
 * 商品を取得します。
 */
interface GetProductQuery {

    /**
     * @param principal principal
     * @param id 商品 ID
     * @return 商品
     * @throws ProductIsNotFoundException
     */
    fun perform(
        principal: User?,
        id: String,
    ): Mono<Product>

    class ProductIsNotFoundException : RuntimeException()

}
