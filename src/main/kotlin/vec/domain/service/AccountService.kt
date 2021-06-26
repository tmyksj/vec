package vec.domain.service

import reactor.core.publisher.Mono
import vec.domain.entity.User

/**
 * アカウント（ユーザ）に関するサービスです。
 */
interface AccountService {

    /**
     * 整合性が取れているかを判定します。
     * @param user ユーザ
     * @return user もしくは empty
     */
    fun isValid(user: User): Mono<User>

    /**
     * ユーザを登録します。
     * @param email メールアドレス
     * @param passwordRaw パスワード
     * @param hasRoleAdmin 管理者ユーザの役割を持つかどうか
     * @param hasRoleConsumer 消費者ユーザの役割を持つかどうか
     * @return 登録されたユーザもしくは empty
     */
    fun register(
        email: String,
        passwordRaw: String,
        hasRoleAdmin: Boolean = false,
        hasRoleConsumer: Boolean = false,
    ): Mono<User>

}
