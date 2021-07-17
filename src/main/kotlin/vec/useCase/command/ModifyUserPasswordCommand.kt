package vec.useCase.command

import reactor.core.publisher.Mono
import vec.domain.entity.User

/**
 * ユーザのパスワードを変更します。
 */
interface ModifyUserPasswordCommand {

    fun perform(request: Request): Mono<Response>

    class Request(

        /**
         * principal
         */
        val principal: User,

        /**
         * 現在のパスワード
         */
        val currentPasswordRaw: String,

        /**
         * 新しいパスワード
         */
        val newPasswordRaw: String,

        )

    class Response

    class PasswordMismatchesException : RuntimeException()

}
