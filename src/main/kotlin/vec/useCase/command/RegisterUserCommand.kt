package vec.useCase.command

import reactor.core.publisher.Mono

/**
 * ユーザを登録します。
 */
interface RegisterUserCommand {

    fun perform(request: Request): Mono<Response>

    class Request(

        /**
         * メールアドレス
         */
        val email: String,

        /**
         * パスワード
         */
        val passwordRaw: String,

        /**
         * 一般ユーザ権限を持つかどうか
         */
        val hasAuthorityGeneral: Boolean,

        )

    class Response

    class AlreadyInUseException : RuntimeException()

}
