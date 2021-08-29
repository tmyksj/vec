package vec.useCase.query

import reactor.core.publisher.Mono
import vec.domain.entity.User
import vec.useCase.dto.StoreAgreementDto

/**
 * 店舗利用同意文書を取得します。
 */
interface GetStoreAgreementQuery {

    /**
     * @param principal principal
     * @return 店舗利用同意文書
     */
    fun perform(
        principal: User?,
    ): Mono<StoreAgreementDto>

}
