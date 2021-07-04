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
         * 管理者ユーザの役割を持つかどうか
         */
        val hasRoleAdmin: Boolean = false,

        /**
         * 消費者ユーザの役割を持つかどうか
         */
        val hasRoleConsumer: Boolean = false,

        )

    class Response

    class EmailIsAlreadyInUseException : RuntimeException()

}
