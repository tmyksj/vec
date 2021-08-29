package vec.useCase.query.impl

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import vec.domain.entity.User
import vec.domain.repository.StoreAgreementRepository
import vec.useCase.dto.StoreAgreementDto
import vec.useCase.query.GetStoreAgreementQuery

@Component
@Transactional
class GetStoreAgreementQueryImpl(
    private val storeAgreementRepository: StoreAgreementRepository,
) : GetStoreAgreementQuery {

    override fun perform(
        principal: User?,
    ): Mono<StoreAgreementDto> {
        return Mono.defer {
            storeAgreementRepository.findCurrentVersion()
        }.map {
            StoreAgreementDto(
                id = it.id,
                body = it.body,
                appliedDate = it.appliedDate,
            )
        }
    }

}
