package vec.presentation.controller

import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.reactive.result.view.Rendering
import org.springframework.web.reactive.result.view.modelAttribute
import reactor.core.publisher.Mono
import vec.domain.entity.User
import vec.useCase.query.GetInformationQuery

@Controller
class IndexController(
    private val getInformationQuery: GetInformationQuery,
) {

    @RequestMapping(method = [RequestMethod.GET], path = ["/"])
    fun get(
        @AuthenticationPrincipal principal: User?,
    ): Mono<Rendering> {
        return Mono.defer {
            getInformationQuery.perform(
                GetInformationQuery.Request()
            )
        }.map {
            Rendering.view("layout/default")
                .modelAttribute("principal", principal)
                .modelAttribute("productList", it.productList)
                .modelAttribute("template", "template/index")
                .status(HttpStatus.OK)
                .build()
        }
    }

}
