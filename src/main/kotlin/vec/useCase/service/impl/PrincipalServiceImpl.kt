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

    override fun reload(): Mono<User> {
        return ReactiveSecurityContextHolder.getContext().flatMap {
            val principal: Any = it.authentication.principal
            check(principal is User)

            Mono.zip(
                Mono.just(it),
                userRepository.findById(principal.id),
            )
        }.doOnNext {
            it.t1.authentication =
                UsernamePasswordAuthenticationToken(it.t2, it.t2.passwordEncrypted, it.t2.authorities)
        }.map {
            it.t2
        }
    }

}
