package vec.domain.service

import reactor.core.publisher.Mono
import vec.domain.entity.User

/**
 * アカウント（ユーザ）に関するサービスです。
 */
interface AccountService {

    /**
     * メールアドレスを変更します。
     * @param user ユーザ
     * @param email メールアドレス
     * @return ユーザ
     * @throws EmailMustBeUniqueException
     */
    fun modifyEmail(
        user: User,
        email: String,
    ): Mono<User>

    /**
     * パスワードを変更します。
     * @param user ユーザ
     * @param currentPasswordRaw 現在のパスワード
     * @param newPasswordRaw 新しいパスワード
     * @return ユーザ
     * @throws PasswordMustMatchException
     */
    fun modifyPassword(
        user: User,
        currentPasswordRaw: String,
        newPasswordRaw: String,
    ): Mono<User>

    /**
     * ユーザを登録します。
     * @param email メールアドレス
     * @param passwordRaw パスワード
     * @param hasRoleAdmin 管理者ユーザの役割を持つかどうか
     * @param hasRoleConsumer 消費者ユーザの役割を持つかどうか
     * @return 登録されたユーザ
     * @throws EmailMustBeUniqueException
     */
    fun register(
        email: String,
        passwordRaw: String,
        hasRoleAdmin: Boolean = false,
        hasRoleConsumer: Boolean = false,
    ): Mono<User>

    class EmailMustBeUniqueException : RuntimeException()

    class PasswordMustMatchException : RuntimeException()

}
