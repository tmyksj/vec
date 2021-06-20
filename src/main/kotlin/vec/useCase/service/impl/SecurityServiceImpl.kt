package vec.useCase.service.impl

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import vec.domain.repository.UserRepository
import vec.useCase.service.SecurityService

@Component
@Transactional
class SecurityServiceImpl(
    private val userRepository: UserRepository
) : SecurityService {

    override fun findByUsername(username: String): Mono<UserDetails> {
        return Mono.defer {
            userRepository.findByEmail(username)
        }
    }

}
