package vec.presentation.controller.order

import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.reactive.result.view.Rendering
import org.springframework.web.server.ServerWebExchange
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.onErrorResume
import vec.domain.entity.User
import vec.presentation.component.RenderingComponent
import vec.useCase.command.OrderCommand
import vec.useCase.query.GetOrderListQuery

@Controller
class IndexController(
    private val renderingComponent: RenderingComponent,
    private val orderCommand: OrderCommand,
    private val getOrderListQuery: GetOrderListQuery,
) {

    @RequestMapping(method = [RequestMethod.GET], path = ["/order"])
    fun get(
        serverWebExchange: ServerWebExchange,
        @AuthenticationPrincipal principal: User,
    ): Mono<Rendering> {
        return Mono.fromCallable {
            getOrderListQuery.perform(
                principal = principal,
            )
        }.flatMap {
            renderingComponent.view("layout/default")
                .modelAttribute("orderList", ReactiveDataDriverContextVariable(it))
                .modelAttribute("principal", principal)
                .modelAttribute("template", "template/order/index")
                .status(HttpStatus.OK)
                .build(serverWebExchange)
        }
    }

    @RequestMapping(method = [RequestMethod.POST], path = ["/order"])
    fun post(
        serverWebExchange: ServerWebExchange,
        @AuthenticationPrincipal principal: User,
    ): Mono<Rendering> {
        return Mono.defer {
            orderCommand.perform(
                principal = principal,
            )
        }.flatMap {
            renderingComponent.redirect("/")
                .status(HttpStatus.SEE_OTHER)
                .build(serverWebExchange)
        }.onErrorResume(OrderCommand.CartIsEmptyException::class) {
            renderingComponent.redirect("/cart")
                .status(HttpStatus.SEE_OTHER)
                .build(serverWebExchange)
        }.onErrorResume(OrderCommand.ProductIsNotFoundException::class) {
            renderingComponent.redirect("/cart")
                .status(HttpStatus.SEE_OTHER)
                .build(serverWebExchange)
        }
    }

}
