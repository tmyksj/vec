package vec.domain.service

import reactor.core.publisher.Mono
import vec.domain.entity.User
import vec.domain.exception.SecurityException

/**
 * セキュリティに関するサービスです。
 */
interface SecurityService {

    /**
     * 管理者ユーザの役割を持つことを要求します。
     * @param user ユーザ
     * @return ユーザ
     * @throws SecurityException
     */
    fun requireRoleAdmin(user: User): Mono<User>

    /**
     * 店員ユーザの役割を持つことを要求します。
     * @param user ユーザ
     * @return ユーザ
     * @throws SecurityException
     */
    fun requireRoleClerk(user: User): Mono<User>

    /**
     * 消費者ユーザの役割を持つことを要求します。
     * @param user ユーザ
     * @return ユーザ
     * @throws SecurityException
     */
    fun requireRoleConsumer(user: User): Mono<User>

}
