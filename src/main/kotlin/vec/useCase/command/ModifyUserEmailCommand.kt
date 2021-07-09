package vec.useCase.command

import reactor.core.publisher.Mono
import vec.domain.entity.User

/**
 * ユーザの email を変更します。
 */
interface ModifyUserEmailCommand {

    fun perform(request: Request): Mono<Response>

    class Request(

        /**
         * principal
         */
        val principal: User,

        /**
         * メールアドレス
         */
        val email: String,

        )

    class Response

    class EmailIsAlreadyInUseException : RuntimeException()

}
