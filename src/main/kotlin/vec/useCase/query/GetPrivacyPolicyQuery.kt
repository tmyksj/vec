package vec.useCase.query

import reactor.core.publisher.Mono

/**
 * プライバシーポリシーを取得します。
 */
interface GetPrivacyPolicyQuery {

    fun perform(request: Request): Mono<Response>

    class Request

    class Response(

        /**
         * 本文
         */
        val body: String,

        )

}
