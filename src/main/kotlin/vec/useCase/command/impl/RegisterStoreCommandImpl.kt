package vec.useCase.command.impl

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.onErrorMap
import vec.domain.entity.User
import vec.domain.service.AccountService
import vec.domain.service.StoreService
import vec.useCase.command.RegisterStoreCommand

@Component
@Transactional
class RegisterStoreCommandImpl(
    private val accountService: AccountService,
    private val storeService: StoreService,
) : RegisterStoreCommand {

    override fun perform(
        principal: User?,
        name: String,
        address: String,
        email: String,
        passwordRaw: String
    ): Mono<User> {
        return Mono.defer {
            Mono.zip(
                storeService.register(
                    name = name,
                    address = address,
                ),
                accountService.register(
                    email = email,
                    passwordRaw = passwordRaw,
                    hasRoleClerk = true,
                ),
            )
        }.onErrorMap(AccountService.EmailMustBeUniqueException::class) {
            RegisterStoreCommand.EmailIsAlreadyInUseException()
        }.flatMap { tuple2 ->
            storeService.addClerk(
                store = tuple2.t1,
                user = tuple2.t2,
            ).map {
                tuple2.t2
            }
        }
    }

}
