package vec.presentation.controller.admin.product

import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.reactive.result.view.Rendering
import org.springframework.web.server.ServerWebExchange
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable
import reactor.core.publisher.Mono
import vec.domain.entity.User
import vec.presentation.component.RenderingComponent
import vec.useCase.query.GetProductListQuery

@Controller
class IndexController(
    private val renderingComponent: RenderingComponent,
    private val getProductListQuery: GetProductListQuery,
) {

    @RequestMapping(method = [RequestMethod.GET], path = ["/admin/product"])
    fun get(
        serverWebExchange: ServerWebExchange,
        @AuthenticationPrincipal principal: User,
    ): Mono<Rendering> {
        return Mono.defer {
            getProductListQuery.perform(
                principal = principal,
            )
        }.flatMap {
            renderingComponent.view("layout/default")
                .modelAttribute("principal", principal)
                .modelAttribute("productList", ReactiveDataDriverContextVariable(it))
                .modelAttribute("template", "template/admin/product/index")
                .status(HttpStatus.OK)
                .build(serverWebExchange)
        }
    }

}
