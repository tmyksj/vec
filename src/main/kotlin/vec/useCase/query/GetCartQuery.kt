package vec.useCase.query

import reactor.core.publisher.Mono
import vec.domain.entity.CartProduct
import vec.domain.entity.Product
import vec.domain.entity.User

/**
 * カートを取得します。
 */
interface GetCartQuery {

    fun perform(request: Request): Mono<Response>

    class Request(

        /**
         * principal
         */
        val principal: User,

        )

    class Response(

        /**
         * カートにある商品のリスト
         */
        val cartProductList: List<CartProduct>,

        /**
         * 商品のリスト
         */
        val productList: List<Product>,

        )

}
