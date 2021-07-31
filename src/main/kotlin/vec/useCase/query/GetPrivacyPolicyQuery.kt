package vec.useCase.query

import reactor.core.publisher.Mono
import vec.domain.entity.User
import vec.useCase.dto.PrivacyPolicyDto

/**
 * プライバシーポリシーを取得します。
 */
interface GetPrivacyPolicyQuery {

    /**
     * @param principal principal
     * @return プライバシーポリシー
     */
    fun perform(
        principal: User?,
    ): Mono<PrivacyPolicyDto>

}
