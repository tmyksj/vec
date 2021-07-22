package vec.presentation.controller.termsOfService

import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.reactive.result.view.Rendering
import org.springframework.web.reactive.result.view.modelAttribute
import reactor.core.publisher.Mono
import vec.domain.entity.User
import vec.useCase.query.GetTermsOfServiceQuery

@Controller
class IndexController(
    private val getTermsOfServiceQuery: GetTermsOfServiceQuery,
) {

    @RequestMapping(method = [RequestMethod.GET], path = ["/terms-of-service"])
    fun get(
        @AuthenticationPrincipal principal: User?,
    ): Mono<Rendering> {
        return Mono.defer {
            getTermsOfServiceQuery.perform(
                principal = principal,
            )
        }.map {
            Rendering.view("layout/default")
                .modelAttribute("body", it.body)
                .modelAttribute("principal", principal)
                .modelAttribute("template", "template/termsOfService/index")
                .status(HttpStatus.OK)
                .build()
        }
    }

}
