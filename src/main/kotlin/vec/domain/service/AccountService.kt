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
     * @param hasAuthorityAdmin 管理者ユーザ権限を持つかどうか
     * @param hasAuthorityConsumer 消費者ユーザ権限を持つかどうか
     * @return 登録されたユーザもしくは empty
     */
    fun register(
        email: String,
        passwordRaw: String,
        hasAuthorityAdmin: Boolean = false,
        hasAuthorityConsumer: Boolean = false,
    ): Mono<User>

}
