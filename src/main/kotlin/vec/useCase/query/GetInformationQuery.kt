package vec.useCase.query

import reactor.core.publisher.Mono
import vec.domain.entity.Product

/**
 * 情報を取得します。
 */
interface GetInformationQuery {

    fun perform(request: Request): Mono<Response>

    class Request

    class Response(

        /**
         * 注目の商品のリスト
         */
        val productList: List<Product>,

        )

}
