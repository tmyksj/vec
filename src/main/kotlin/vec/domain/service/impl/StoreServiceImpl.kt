package vec.domain.service.impl

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import vec.domain.entity.Clerk
import vec.domain.entity.Store
import vec.domain.entity.User
import vec.domain.repository.ClerkRepository
import vec.domain.repository.StoreRepository
import vec.domain.repository.UserRepository
import vec.domain.service.StoreService
import java.time.LocalDateTime

@Component
@Transactional
class StoreServiceImpl(
    private val clerkRepository: ClerkRepository,
    private val storeRepository: StoreRepository,
    private val userRepository: UserRepository,
) : StoreService {

    override fun addClerk(
        store: Store,
        user: User,
    ): Mono<Clerk> {
        return Mono.defer {
            storeRepository.findById(store.id)
        }.switchIfEmpty {
            throw StoreService.StoreMustExistException()
        }.flatMap {
            userRepository.findById(user.id)
        }.switchIfEmpty {
            throw StoreService.UserMustExistException()
        }.flatMap {
            clerkRepository.countByUserId(user.id)
        }.flatMap {
            if (it == 0L) {
                clerkRepository.save(
                    Clerk(
                        storeId = store.id,
                        userId = user.id,
                    )
                )
            } else {
                clerkRepository.findByStoreIdAndUserId(store.id, user.id)
                    .switchIfEmpty {
                        throw StoreService.UserMustBeUniqueException()
                    }
            }
        }
    }

    override fun register(
        name: String,
        address: String,
    ): Mono<Store> {
        return Mono.defer {
            storeRepository.save(
                Store(
                    name = name,
                    address = address,
                    registeredDate = LocalDateTime.now(),
                )
            )
        }
    }

}
