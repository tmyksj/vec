package vec.useCase.query

import reactor.core.publisher.Mono
import vec.domain.entity.User
import vec.useCase.dto.UsageStatisticsDto

/**
 * 利用統計を取得します。
 */
interface GetUsageStatisticsQuery {

    /**
     * @param principal principal
     * @return 利用統計
     */
    fun perform(
        principal: User,
    ): Mono<UsageStatisticsDto>

}
