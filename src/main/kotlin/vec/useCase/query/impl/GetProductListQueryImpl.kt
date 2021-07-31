package vec.useCase.query.impl

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import vec.domain.entity.User
import vec.domain.repository.ProductRepository
import vec.useCase.dto.ProductDto
import vec.useCase.query.GetProductListQuery

@Component
@Transactional
class GetProductListQueryImpl(
    private val productRepository: ProductRepository,
) : GetProductListQuery {

    override fun perform(
        principal: User?,
    ): Flux<ProductDto> {
        return Flux.defer {
            productRepository.findAll()
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
