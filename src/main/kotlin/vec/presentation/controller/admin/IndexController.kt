package vec.presentation.controller.admin

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
import vec.useCase.query.GetUsageStatisticsQuery

@Controller
class IndexController(
    private val renderingComponent: RenderingComponent,
    private val getUsageStatisticsQuery: GetUsageStatisticsQuery,
) {

    @RequestMapping(method = [RequestMethod.GET], path = ["/admin"])
    fun get(
        serverWebExchange: ServerWebExchange,
        @AuthenticationPrincipal principal: User,
    ): Mono<Rendering> {
        return Mono.defer {
            getUsageStatisticsQuery.perform(
                principal = principal,
            )
        }.flatMap {
            renderingComponent.view("layout/default")
                .modelAttribute("principal", principal)
                .modelAttribute("template", "template/admin/index")
                .modelAttribute("usageStatistics", it)
                .status(HttpStatus.OK)
                .build(serverWebExchange)
        }
    }

}
