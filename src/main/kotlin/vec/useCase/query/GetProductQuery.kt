package vec.useCase.query

import reactor.core.publisher.Mono
import vec.domain.entity.Product
import vec.domain.entity.User

/**
 * 商品を取得します。
 */
interface GetProductQuery {

    fun perform(request: Request): Mono<Response>

    class Request(

        /**
         * principal
         */
        val principal: User?,

        /**
         * 商品 ID
         */
        val id: String,

        )

    class Response(

        /**
         * 商品
         */
        val product: Product,

        )

    class ProductIsNotFoundException : RuntimeException()

}
