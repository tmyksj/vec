package vec.useCase.command.impl

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.onErrorMap
import reactor.kotlin.core.publisher.switchIfEmpty
import vec.domain.entity.Product
import vec.domain.entity.User
import vec.domain.repository.ProductRepository
import vec.useCase.command.ModifyProductCommand
import vec.useCase.dto.ProductDto
import java.math.BigDecimal

@Component
@Transactional
class ModifyProductCommandImpl(
    private val productRepository: ProductRepository,
) : ModifyProductCommand {

    override fun perform(
        principal: User,
        id: String,
        name: String,
        description: String,
        amount: Long,
        taxRate: BigDecimal,
        stock: Long
    ): Mono<ProductDto> {
        return Mono.defer {
            productRepository.findById(id)
        }.switchIfEmpty {
            throw ModifyProductCommand.ProductIsNotFoundException()
        }.map {
            it.modifyName(name)
                .modifyDescription(description)
                .modifyAmount(amount)
                .modifyTaxRate(taxRate)
                .increaseStock(stock)
        }.onErrorMap(Product.StockMustBeEnoughException::class) {
            ModifyProductCommand.StockMustBeEnoughException()
        }.flatMap {
            productRepository.save(it)
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
