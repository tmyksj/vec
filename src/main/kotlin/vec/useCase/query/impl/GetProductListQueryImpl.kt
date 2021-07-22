package vec.useCase.query.impl

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import vec.domain.entity.Product
import vec.domain.entity.User
import vec.domain.repository.ProductRepository
import vec.useCase.query.GetProductListQuery

@Component
@Transactional
class GetProductListQueryImpl(
    private val productRepository: ProductRepository,
) : GetProductListQuery {

    override fun perform(
        principal: User?,
    ): Flux<Product> {
        return Flux.defer {
            productRepository.findAll()
        }
    }

}
