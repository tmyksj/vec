package vec.useCase.command

import reactor.core.publisher.Mono
import vec.domain.entity.User

/**
 * ユーザを登録します。
 */
interface RegisterUserCommand {

    /**
     * @param principal principal
     * @param email メールアドレス
     * @param passwordRaw パスワード
     * @param hasRoleAdmin 管理者ユーザの役割を持つかどうか
     * @param hasRoleConsumer 消費者ユーザの役割を持つかどうか
     * @return ユーザ
     * @throws EmailIsAlreadyInUseException
     */
    fun perform(
        principal: User?,
        email: String,
        passwordRaw: String,
        hasRoleAdmin: Boolean = false,
        hasRoleConsumer: Boolean = false,
    ): Mono<User>

    class EmailIsAlreadyInUseException : RuntimeException()

}
