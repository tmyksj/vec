package vec.presentation.controller.order

import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.reactive.result.view.Rendering
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.onErrorResume
import reactor.kotlin.core.publisher.toMono
import vec.domain.entity.User
import vec.useCase.command.OrderCommand

@Controller
class IndexController(
    private val orderCommand: OrderCommand,
) {

    @RequestMapping(method = [RequestMethod.POST], path = ["/order"])
    fun post(
        @AuthenticationPrincipal principal: User,
    ): Mono<Rendering> {
        return Mono.defer {
            orderCommand.perform(
                principal = principal,
            )
        }.map {
            Rendering.redirectTo("/")
                .status(HttpStatus.SEE_OTHER)
                .build()
        }.onErrorResume(OrderCommand.CartIsEmptyException::class) {
            Rendering.redirectTo("/cart")
                .status(HttpStatus.SEE_OTHER)
                .build()
                .toMono()
        }.onErrorResume(OrderCommand.ProductIsNotFoundException::class) {
            Rendering.redirectTo("/cart")
                .status(HttpStatus.SEE_OTHER)
                .build()
                .toMono()
        }
    }

}
