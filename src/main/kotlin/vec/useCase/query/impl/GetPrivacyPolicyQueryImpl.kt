package vec.useCase.query.impl

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import vec.domain.entity.User
import vec.domain.repository.PrivacyPolicyRepository
import vec.useCase.dto.PrivacyPolicyDto
import vec.useCase.query.GetPrivacyPolicyQuery

@Component
@Transactional
class GetPrivacyPolicyQueryImpl(
    private val privacyPolicyRepository: PrivacyPolicyRepository,
) : GetPrivacyPolicyQuery {

    override fun perform(
        principal: User?,
    ): Mono<PrivacyPolicyDto> {
        return Mono.defer {
            privacyPolicyRepository.findCurrentVersion()
        }.map {
            PrivacyPolicyDto(
                id = it.id,
                body = it.body,
                appliedDate = it.appliedDate,
            )
        }
    }

}
