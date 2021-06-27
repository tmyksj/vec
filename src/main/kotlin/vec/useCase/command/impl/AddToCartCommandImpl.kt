package vec.useCase.command.impl

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import vec.domain.repository.ProductRepository
import vec.domain.service.ECommerceCartService
import vec.useCase.command.AddToCartCommand

@Component
@Transactional
class AddToCartCommandImpl(
    private val productRepository: ProductRepository,
    private val eCommerceCartService: ECommerceCartService,
) : AddToCartCommand {

    override fun perform(
        request: AddToCartCommand.Request,
    ): Mono<AddToCartCommand.Response> {
        return Mono.defer {
            productRepository.findById(request.productId)
        }.flatMap {
            eCommerceCartService.add(request.principal, it)
        }.switchIfEmpty {
            throw AddToCartCommand.NotFoundException()
        }.map {
            AddToCartCommand.Response()
        }
    }

}
