package vec.useCase.command.impl

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import vec.domain.entity.CartProduct
import vec.domain.entity.User
import vec.domain.repository.ProductRepository
import vec.domain.service.ECommerceCartService
import vec.useCase.command.AddToCartCommand

@Component
@Transactional
class AddToCartCommandImpl(
    private val productRepository: ProductRepository,
    private val eCommerceCartService: ECommerceCartService,
) : AddToCartCommand {

    override fun perform(
        principal: User,
        productId: String,
        quantity: Long,
    ): Mono<CartProduct> {
        return Mono.defer {
            productRepository.findById(productId)
        }.switchIfEmpty {
            throw AddToCartCommand.ProductIsNotFoundException()
        }.flatMap {
            eCommerceCartService.add(principal, it, quantity)
        }
    }

}
