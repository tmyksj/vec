package vec.domain.service.impl

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import vec.domain.entity.User
import vec.domain.exception.SecurityException
import vec.domain.service.SecurityService

@Component
@Transactional
class SecurityServiceImpl : SecurityService {

    override fun requireRoleAdmin(user: User): Mono<User> {
        return Mono.fromCallable {
            if (user.hasRoleAdmin) {
                user
            } else {
                throw SecurityException()
            }
        }
    }

    override fun requireRoleConsumer(user: User): Mono<User> {
        return Mono.fromCallable {
            if (user.hasRoleConsumer) {
                user
            } else {
                throw SecurityException()
            }
        }
    }

}
