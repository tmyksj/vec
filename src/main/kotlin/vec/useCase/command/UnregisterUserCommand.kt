package vec.useCase.command

import reactor.core.publisher.Mono
import vec.domain.entity.User

/**
 * サービスの利用停止処理をします。
 */
interface UnregisterUserCommand {

    fun perform(request: Request): Mono<Response>

    class Request(

        /**
         * principal
         */
        val principal: User,

        )

    class Response

}
