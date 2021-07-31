package vec.useCase.command.impl

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import vec.domain.entity.User
import vec.domain.repository.ProductRepository
import vec.domain.service.ECommerceCartService
import vec.useCase.command.AddToCartCommand
import vec.useCase.dto.CartProductDto

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
    ): Mono<CartProductDto> {
        return Mono.defer {
            productRepository.findById(productId)
        }.switchIfEmpty {
            throw AddToCartCommand.ProductIsNotFoundException()
        }.flatMap { product ->
            eCommerceCartService.add(principal, product, quantity)
                .map {
                    CartProductDto(
                        id = it.id,
                        productId = product.id,
                        productName = product.name,
                        productDescription = product.description,
                        productAmount = product.amount,
                        productTaxRate = product.taxRate,
                        productTax = product.tax,
                        productTotal = product.total,
                        productStock = product.stock,
                        quantity = it.quantity,
                        addedDate = it.addedDate,
                    )
                }
        }
    }

}
