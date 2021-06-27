package vec.useCase.service.impl

import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import vec.domain.repository.UserRepository

@Component
@Transactional
class ReactiveUserDetailsServiceImpl(
    private val userRepository: UserRepository,
) : ReactiveUserDetailsService {

    override fun findByUsername(username: String): Mono<UserDetails> {
        return Mono.defer {
            userRepository.findByEmail(username)
        }
    }

}
