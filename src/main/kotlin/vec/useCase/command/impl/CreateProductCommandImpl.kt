package vec.useCase.command.impl

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import vec.domain.entity.Product
import vec.domain.entity.User
import vec.domain.repository.ProductRepository
import vec.useCase.command.CreateProductCommand
import vec.useCase.dto.ProductDto
import java.math.BigDecimal

@Component
@Transactional
class CreateProductCommandImpl(
    private val productRepository: ProductRepository,
) : CreateProductCommand {

    override fun perform(
        principal: User,
        name: String,
        description: String,
        amount: Long,
        taxRate: BigDecimal,
        stock: Long
    ): Mono<ProductDto> {
        return Mono.defer {
            productRepository.save(
                Product(
                    name = name,
                    description = description,
                    amount = amount,
                    taxRate = taxRate,
                    stock = stock,
                )
            )
        }.map {
            ProductDto(
                id = it.id,
                name = it.name,
                description = it.description,
                amount = it.amount,
                taxRate = it.taxRate,
                tax = it.tax,
                total = it.total,
                stock = it.stock,
            )
        }
    }

}
