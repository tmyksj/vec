package vec.useCase.query.impl

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import vec.domain.entity.TermsOfService
import vec.domain.entity.User
import vec.domain.repository.TermsOfServiceRepository
import vec.useCase.query.GetTermsOfServiceQuery

@Component
@Transactional
class GetTermsOfServiceQueryImpl(
    private val termsOfServiceRepository: TermsOfServiceRepository,
) : GetTermsOfServiceQuery {

    override fun perform(
        principal: User?,
    ): Mono<TermsOfService> {
        return Mono.defer {
            termsOfServiceRepository.findCurrentVersion()
        }
    }

}
