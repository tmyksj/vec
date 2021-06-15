package vec.presentation.controller

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.reactive.result.view.Rendering
import reactor.core.publisher.Mono
import vec.useCase.query.GetInformationQuery

@Controller
class IndexController(
    private val getInformationQuery: GetInformationQuery,
) {

    @RequestMapping(method = [RequestMethod.GET], path = ["/"])
    fun get(): Mono<Rendering> {
        return Mono.defer {
            getInformationQuery.perform(
                GetInformationQuery.Request()
            )
        }.map {
            Rendering.view("layout/default")
                .modelAttribute("productList", it.productList)
                .modelAttribute("template", "template/index")
                .status(HttpStatus.OK)
                .build()
        }
    }

}
