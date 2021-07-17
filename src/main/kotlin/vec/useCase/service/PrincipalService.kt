package vec.useCase.service

import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import vec.domain.entity.User

/**
 * Principal に関するサービスです。
 */
interface PrincipalService {

    /**
     * Principal を clear します。
     * @param serverWebExchange serverWebExchange
     * @return clear されたユーザ
     */
    fun clear(serverWebExchange: ServerWebExchange): Mono<User>

    /**
     * Principal を reload します。
     * @param serverWebExchange serverWebExchange
     * @return reload されたユーザ
     */
    fun reload(serverWebExchange: ServerWebExchange): Mono<User>

}
