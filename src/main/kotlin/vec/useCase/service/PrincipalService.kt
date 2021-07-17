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
     */
    fun clear(serverWebExchange: ServerWebExchange): Mono<User>

    /**
     * Principal を reload します。
     */
    fun reload(): Mono<User>

}
