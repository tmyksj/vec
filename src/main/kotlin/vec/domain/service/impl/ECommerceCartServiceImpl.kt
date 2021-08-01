package vec.domain.service.impl

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import vec.domain.entity.Cart
import vec.domain.entity.CartProduct
import vec.domain.entity.Product
import vec.domain.entity.User
import vec.domain.repository.CartProductRepository
import vec.domain.repository.CartRepository
import vec.domain.repository.ProductRepository
import vec.domain.service.ECommerceCartService
import vec.domain.service.SecurityService
import java.time.LocalDateTime

@Component
@Transactional
class ECommerceCartServiceImpl(
    private val cartProductRepository: CartProductRepository,
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
    private val securityService: SecurityService,
) : ECommerceCartService {

    override fun add(user: User, product: Product, quantity: Long): Mono<CartProduct> {
        return Mono.defer {
            securityService.requireRoleConsumer(user)
        }.flatMap {
            productRepository.findById(product.id)
        }.flatMap {
            cartRepository.findByUserId(user.id)
                .switchIfEmpty {
                    cartRepository.save(
                        Cart(
                            userId = user.id,
                        )
                    )
                }
        }.flatMap {
            cartProductRepository.findByCartIdAndProductId(it.id, product.id)
                .switchIfEmpty {
                    Mono.just(
                        CartProduct(
                            cartId = it.id,
                            productId = product.id,
                            quantity = 0L,
                            addedDate = LocalDateTime.now(),
                        )
                    )
                }
        }.map {
            it.increaseQuantity(quantity)
        }.flatMap {
            cartProductRepository.save(it)
        }
    }

}
