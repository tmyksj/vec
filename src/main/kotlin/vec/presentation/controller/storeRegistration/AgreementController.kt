package vec.presentation.controller.storeRegistration

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
import vec.useCase.query.GetStoreAgreementQuery

@Controller
class AgreementController(
    private val renderingComponent: RenderingComponent,
    private val getStoreAgreementQuery: GetStoreAgreementQuery,
) {

    @RequestMapping(method = [RequestMethod.GET], path = ["/store-registration/agreement"])
    fun get(
        serverWebExchange: ServerWebExchange,
        @AuthenticationPrincipal principal: User?,
    ): Mono<Rendering> {
        if (principal != null) {
            return Mono.defer {
                renderingComponent.redirect("/")
                    .status(HttpStatus.SEE_OTHER)
                    .build(serverWebExchange)
            }
        }

        return Mono.defer {
            getStoreAgreementQuery.perform(
                principal = principal,
            )
        }.flatMap {
            renderingComponent.view("layout/default")
                .modelAttribute("body", it.body)
                .modelAttribute("principal", principal)
                .modelAttribute("template", "template/storeRegistration/agreement")
                .status(HttpStatus.OK)
                .build(serverWebExchange)
        }
    }

}
