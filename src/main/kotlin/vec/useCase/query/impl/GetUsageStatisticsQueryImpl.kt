package vec.useCase.query.impl

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import vec.domain.entity.User
import vec.domain.repository.OrderRepository
import vec.domain.repository.ProductRepository
import vec.domain.repository.UserRepository
import vec.useCase.dto.UsageStatisticsDto
import vec.useCase.query.GetUsageStatisticsQuery

@Component
@Transactional
class GetUsageStatisticsQueryImpl(
    private val orderRepository: OrderRepository,
    private val productRepository: ProductRepository,
    private val userRepository: UserRepository,
) : GetUsageStatisticsQuery {

    override fun perform(
        principal: User,
    ): Mono<UsageStatisticsDto> {
        return Mono.defer {
            Mono.zip(
                userRepository.countByHasRoleConsumer(true),
                productRepository.count(),
                orderRepository.count(),
            )
        }.map {
            UsageStatisticsDto(
                consumerCount = it.t1,
                productCount = it.t2,
                orderCount = it.t3,
            )
        }
    }

}
