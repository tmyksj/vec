package vec.presentation.controller.cart

import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.reactive.result.view.Rendering
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.ServerWebInputException
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.onErrorMap
import vec.domain.entity.User
import vec.presentation.component.RenderingComponent
import vec.presentation.form.cart.IndexForm
import vec.useCase.command.AddToCartCommand
import vec.useCase.query.GetCartQuery

@Controller
class IndexController(
    private val renderingComponent: RenderingComponent,
    private val addToCartCommand: AddToCartCommand,
    private val getCartQuery: GetCartQuery,
) {

    @RequestMapping(method = [RequestMethod.GET], path = ["/cart"])
    fun get(
        serverWebExchange: ServerWebExchange,
        @AuthenticationPrincipal principal: User,
    ): Mono<Rendering> {
        return Mono.fromCallable {
            getCartQuery.perform(
                principal = principal,
            )
        }.flatMap {
            renderingComponent.view("layout/default")
                .modelAttribute("cartProductList", ReactiveDataDriverContextVariable(it))
                .modelAttribute("principal", principal)
                .modelAttribute("template", "template/cart/index")
                .status(HttpStatus.OK)
                .build(serverWebExchange)
        }
    }

    @RequestMapping(method = [RequestMethod.POST], path = ["/cart"])
    fun post(
        serverWebExchange: ServerWebExchange,
        @AuthenticationPrincipal principal: User,
        @Validated indexForm: IndexForm,
        bindingResult: BindingResult,
    ): Mono<Rendering> {
        return Mono.defer {
            if (bindingResult.hasErrors()) {
                throw ServerWebInputException(bindingResult.toString())
            }

            addToCartCommand.perform(
                principal = principal,
                productId = checkNotNull(indexForm.productId),
                quantity = 1L,
            )
        }.flatMap {
            renderingComponent.redirect("/cart")
                .redirectAttribute("info", "vec.presentation.controller.cart.IndexController.post.ok")
                .status(HttpStatus.SEE_OTHER)
                .build(serverWebExchange)
        }.onErrorMap(ServerWebInputException::class) {
            ResponseStatusException(HttpStatus.BAD_REQUEST)
        }.onErrorMap(AddToCartCommand.ProductIsNotFoundException::class) {
            ResponseStatusException(HttpStatus.BAD_REQUEST)
        }
    }

}
