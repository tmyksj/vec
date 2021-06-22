package vec.useCase.command

import reactor.core.publisher.Mono
import vec.domain.entity.User

/**
 * ユーザを登録します。
 */
interface RegisterUserCommand {

    fun perform(request: Request): Mono<Response>

    class Request(

        /**
         * principal
         */
        val principal: User?,

        /**
         * メールアドレス
         */
        val email: String,

        /**
         * パスワード
         */
        val passwordRaw: String,

        /**
         * 管理者ユーザ権限を持つかどうか
         */
        val hasAuthorityAdmin: Boolean = false,

        /**
         * 消費者ユーザ権限を持つかどうか
         */
        val hasAuthorityConsumer: Boolean = false,

        )

    class Response

    class AlreadyInUseException : RuntimeException()

}
