package vec.presentation.controller.signIn

import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.reactive.result.view.Rendering
import org.springframework.web.reactive.result.view.modelAttribute
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import vec.domain.entity.User

@Controller
class IndexController {

    @RequestMapping(method = [RequestMethod.GET], path = ["/sign-in"])
    fun get(
        @AuthenticationPrincipal principal: User?,
    ): Mono<Rendering> {
        if (principal != null) {
            return Rendering.redirectTo("/")
                .build()
                .toMono()
        }

        return Mono.just(
            Rendering.view("layout/default")
                .modelAttribute("principal", null)
                .modelAttribute("template", "template/signIn/index")
                .status(HttpStatus.OK)
                .build()
        )
    }

}
