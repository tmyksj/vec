package vec.domain.repository

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import vec.domain.entity.User

@Component
@Transactional
interface UserRepository : ReactiveCrudRepository<User, String> {

    fun countByHasRoleAdmin(hasRoleAdmin: Boolean): Mono<Long>

    fun findByEmail(email: String): Mono<User>

}
