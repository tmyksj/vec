package vec.useCase.query.impl

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import vec.domain.repository.PrivacyPolicyRepository
import vec.useCase.query.GetPrivacyPolicyQuery

@Component
@Transactional
class GetPrivacyPolicyQueryImpl(
    private val privacyPolicyRepository: PrivacyPolicyRepository,
) : GetPrivacyPolicyQuery {

    override fun perform(
        request: GetPrivacyPolicyQuery.Request,
    ): Mono<GetPrivacyPolicyQuery.Response> {
        return Mono.defer {
            privacyPolicyRepository.findCurrentVersion()
        }.map {
            GetPrivacyPolicyQuery.Response(
                body = it.body,
            )
        }
    }

}
