package vec.useCase.query.impl

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import vec.domain.repository.ProductRepository
import vec.useCase.query.GetInformationQuery

@Component
@Transactional
class GetInformationQueryImpl(
    private val productRepository: ProductRepository,
) : GetInformationQuery {

    override fun perform(
        request: GetInformationQuery.Request,
    ): Mono<GetInformationQuery.Response> {
        return productRepository.findAll()
            .collectList()
            .map {
                GetInformationQuery.Response(
                    productList = it,
                )
            }
    }

}
