package vec.presentation.controller.signIn

import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.reactive.result.view.Rendering
import org.springframework.web.reactive.result.view.modelAttribute
import reactor.core.publisher.Mono
import vec.domain.entity.User

@Controller
class IndexController {

    @RequestMapping(method = [RequestMethod.GET], path = ["/sign-in"])
    fun get(
        @AuthenticationPrincipal principal: User?,
    ): Mono<Rendering> {
        if (principal != null) {
            return Mono.fromCallable {
                Rendering.redirectTo("/")
                    .status(HttpStatus.SEE_OTHER)
                    .build()
            }
        }

        return Mono.fromCallable {
            Rendering.view("layout/default")
                .modelAttribute("principal", null)
                .modelAttribute("template", "template/signIn/index")
                .status(HttpStatus.OK)
                .build()
        }
    }

}
