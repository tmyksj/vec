package vec.useCase.query

import reactor.core.publisher.Mono
import vec.domain.entity.User
import vec.useCase.dto.TermsOfServiceDto

/**
 * 利用規約を取得します。
 */
interface GetTermsOfServiceQuery {

    /**
     * @param principal principal
     * @return 利用規約
     */
    fun perform(
        principal: User?,
    ): Mono<TermsOfServiceDto>

}
