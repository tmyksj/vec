package vec.domain.service

import reactor.core.publisher.Mono
import vec.domain.entity.User

/**
 * セキュリティに関するサービスです。
 */
interface SecurityService {

    /**
     * 管理者ユーザ権限を持つことを要求します。
     * @param user ユーザ
     * @return ユーザまたは error (SecurityException)
     */
    fun requireAuthorityAdmin(user: User): Mono<User>

    /**
     * 消費者ユーザ権限を持つことを要求します。
     * @param user ユーザ
     * @return ユーザまたは error (SecurityException)
     */
    fun requireAuthorityConsumer(user: User): Mono<User>

}
