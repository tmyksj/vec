package vec.useCase.query.impl

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import reactor.kotlin.core.publisher.toMono
import vec.domain.entity.CartProduct
import vec.domain.entity.Product
import vec.domain.repository.CartProductRepository
import vec.domain.repository.CartRepository
import vec.domain.repository.ProductRepository
import vec.useCase.query.GetCartQuery

@Component
@Transactional
class GetCartQueryImpl(
    private val cartProductRepository: CartProductRepository,
    private val cartRepository: CartRepository,
    private val productRepository: ProductRepository,
) : GetCartQuery {

    override fun perform(
        request: GetCartQuery.Request,
    ): Mono<GetCartQuery.Response> {
        return Mono.defer {
            cartRepository.findByUserId(request.principal.id)
        }.flatMap { cart ->
            val cartProductFlux: Flux<CartProduct> = cartProductRepository.findAllByCartId(cart.id)
            val productFlux: Flux<Product> = productRepository.findAllById(cartProductFlux.map { it.productId })

            Mono.zip(
                cartProductFlux.collectList(),
                productFlux.collectList(),
            )
        }.map {
            GetCartQuery.Response(
                cartProductList = it.t1,
                productList = it.t2,
            )
        }.switchIfEmpty {
            GetCartQuery.Response(
                cartProductList = listOf(),
                productList = listOf(),
            ).toMono()
        }
    }

}
