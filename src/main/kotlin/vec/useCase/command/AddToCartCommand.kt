package vec.useCase.command

import reactor.core.publisher.Mono
import vec.domain.entity.User

/**
 * カートに商品を追加します。
 */
interface AddToCartCommand {

    fun perform(request: Request): Mono<Response>

    class Request(

        /**
         * principal
         */
        val principal: User,

        /**
         * 商品 ID
         */
        val productId: String,

        )

    class Response

    class NotFoundException : RuntimeException()

}
