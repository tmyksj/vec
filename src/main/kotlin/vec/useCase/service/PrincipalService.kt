package vec.useCase.service

import reactor.core.publisher.Mono
import vec.domain.entity.User

/**
 * Principal に関するサービスです。
 */
interface PrincipalService {

    /**
     * Principal を reload します。
     */
    fun reload(): Mono<User>

}
