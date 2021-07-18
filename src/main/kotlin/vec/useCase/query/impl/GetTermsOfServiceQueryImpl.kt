package vec.useCase.query.impl

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import vec.domain.repository.TermsOfServiceRepository
import vec.useCase.query.GetTermsOfServiceQuery

@Component
@Transactional
class GetTermsOfServiceQueryImpl(
    private val termsOfServiceRepository: TermsOfServiceRepository,
) : GetTermsOfServiceQuery {

    override fun perform(
        request: GetTermsOfServiceQuery.Request,
    ): Mono<GetTermsOfServiceQuery.Response> {
        return Mono.defer {
            termsOfServiceRepository.findCurrentVersion()
        }.map {
            GetTermsOfServiceQuery.Response(
                body = it.body,
            )
        }
    }

}
