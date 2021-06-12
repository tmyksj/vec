package vec.useCase.query.impl

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import vec.domain.repository.ProductRepository
import vec.useCase.query.GetProductQuery

@Component
@Transactional
class GetProductQueryImpl(
    private val productRepository: ProductRepository,
) : GetProductQuery {

    override fun perform(
        request: GetProductQuery.Request,
    ): Mono<GetProductQuery.Response> {
        return productRepository.findById(request.id)
            .map {
                GetProductQuery.Response(
                    product = it,
                )
            }.switchIfEmpty {
                throw GetProductQuery.NotFoundException()
            }
    }

}
