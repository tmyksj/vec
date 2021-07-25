package vec.presentation.controller.account

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

    @RequestMapping(method = [RequestMethod.GET], path = ["/account"])
    fun get(
        serverWebExchange: ServerWebExchange,
        @AuthenticationPrincipal principal: User,
    ): Mono<Rendering> {
        return Mono.defer {
            renderingComponent.view("layout/default")
                .modelAttribute("principal", principal)
                .modelAttribute("template", "template/account/index")
                .status(HttpStatus.OK)
                .build(serverWebExchange)
        }
    }

}
