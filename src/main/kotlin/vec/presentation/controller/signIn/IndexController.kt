package vec.presentation.controller.signIn

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

@Controller
class IndexController(
    private val renderingComponent: RenderingComponent,
) {

    @RequestMapping(method = [RequestMethod.GET], path = ["/sign-in"])
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
            renderingComponent.view("layout/default")
                .modelAttribute("principal", null)
                .modelAttribute("template", "template/signIn/index")
                .status(HttpStatus.OK)
                .build(serverWebExchange)
        }
    }

}
