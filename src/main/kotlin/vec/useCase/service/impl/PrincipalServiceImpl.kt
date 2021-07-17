package vec.useCase.service.impl

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import vec.domain.entity.User
import vec.domain.repository.UserRepository
import vec.useCase.service.PrincipalService

@Component
@Transactional
class PrincipalServiceImpl(
    private val userRepository: UserRepository,
) : PrincipalService {

    override fun clear(serverWebExchange: ServerWebExchange): Mono<User> {
        return ReactiveSecurityContextHolder.getContext().flatMap {
            val principal: Any = it.authentication.principal
            check(principal is User)

            WebSessionServerSecurityContextRepository()
                .save(serverWebExchange, null)
                .thenReturn(principal)
        }
    }

    override fun reload(serverWebExchange: ServerWebExchange): Mono<User> {
        return ReactiveSecurityContextHolder.getContext().flatMap { securityContext ->
            val principal: Any = securityContext.authentication.principal
            check(principal is User)

            userRepository.findById(principal.id)
                .doOnNext {
                    securityContext.authentication =
                        UsernamePasswordAuthenticationToken(it, it.passwordEncrypted, it.authorities)
                }
        }
    }

}
