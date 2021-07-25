package vec.presentation.controller.privacyPolicy

import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.reactive.result.view.Rendering
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import vec.domain.entity.User
import vec.presentation.component.RenderingComponent
import vec.useCase.query.GetPrivacyPolicyQuery

@Controller
class IndexController(
    private val renderingComponent: RenderingComponent,
    private val getPrivacyPolicyQuery: GetPrivacyPolicyQuery,
) {

    @RequestMapping(method = [RequestMethod.GET], path = ["/privacy-policy"])
    fun get(
        serverWebExchange: ServerWebExchange,
        @AuthenticationPrincipal principal: User?,
    ): Mono<Rendering> {
        return Mono.defer {
            getPrivacyPolicyQuery.perform(
                principal = principal,
            )
        }.flatMap {
            renderingComponent.view("layout/default")
                .modelAttribute("body", it.body)
                .modelAttribute("principal", principal)
                .modelAttribute("template", "template/privacyPolicy/index")
                .status(HttpStatus.OK)
                .build(serverWebExchange)
        }
    }

}
