package vec.domain.service

import reactor.core.publisher.Mono
import vec.domain.entity.User

/**
 * セキュリティに関するサービスです。
 */
interface SecurityService {

    /**
     * 管理者ユーザの役割を持つことを要求します。
     * @param user ユーザ
     * @return ユーザもしくは error (SecurityException)
     */
    fun requireRoleAdmin(user: User): Mono<User>

    /**
     * 消費者ユーザの役割を持つことを要求します。
     * @param user ユーザ
     * @return ユーザもしくは error (SecurityException)
     */
    fun requireRoleConsumer(user: User): Mono<User>

}
