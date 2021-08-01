package vec.useCase.query.impl

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import vec.domain.entity.User
import vec.domain.repository.ProductRepository
import vec.useCase.dto.ProductDto
import vec.useCase.query.GetProductQuery

@Component
@Transactional
class GetProductQueryImpl(
    private val productRepository: ProductRepository,
) : GetProductQuery {

    override fun perform(
        principal: User?,
        id: String,
    ): Mono<ProductDto> {
        return Mono.defer {
            productRepository.findById(id)
        }.switchIfEmpty {
            throw GetProductQuery.ProductIsNotFoundException()
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
