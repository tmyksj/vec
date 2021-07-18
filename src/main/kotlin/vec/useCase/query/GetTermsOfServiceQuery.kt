package vec.useCase.query

import reactor.core.publisher.Mono

/**
 * 利用規約を取得します。
 */
interface GetTermsOfServiceQuery {

    fun perform(request: Request): Mono<Response>

    class Request

    class Response(

        /**
         * 本文
         */
        val body: String,

        )

}
