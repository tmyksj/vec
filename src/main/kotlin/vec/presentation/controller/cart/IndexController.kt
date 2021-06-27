package vec.presentation.controller.cart

import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.reactive.result.view.Rendering
import org.springframework.web.server.ServerWebInputException
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.onErrorMap
import vec.domain.entity.User
import vec.presentation.form.cart.IndexForm
import vec.useCase.command.AddToCartCommand
import vec.useCase.query.GetCartQuery

@Controller
class IndexController(
    private val addToCartCommand: AddToCartCommand,
    private val getCartQuery: GetCartQuery,
) {

    @RequestMapping(method = [RequestMethod.GET], path = ["/cart"])
    fun get(
        @AuthenticationPrincipal principal: User,
    ): Mono<Rendering> {
        return Mono.defer {
            getCartQuery.perform(
                GetCartQuery.Request(
                    principal = principal,
                )
            )
        }.map { response ->
            Rendering.view("layout/default")
                .modelAttribute("cartProductList", response.cartProductList)
                .modelAttribute("principal", principal)
                .modelAttribute("productMap", response.productList.associateBy { it.id })
                .modelAttribute("template", "template/cart/index")
                .status(HttpStatus.OK)
                .build()
        }
    }

    @RequestMapping(method = [RequestMethod.POST], path = ["/cart"])
    fun post(
        @AuthenticationPrincipal principal: User,
        @Validated indexForm: IndexForm,
        bindingResult: BindingResult,
    ): Mono<Rendering> {
        return Mono.defer {
            if (bindingResult.hasErrors()) {
                throw ServerWebInputException(bindingResult.toString())
            }

            addToCartCommand.perform(
                AddToCartCommand.Request(
                    principal = principal,
                    productId = checkNotNull(indexForm.productId),
                )
            )
        }.map {
            Rendering.redirectTo("/cart")
                .status(HttpStatus.SEE_OTHER)
                .build()
        }.onErrorMap(AddToCartCommand.NotFoundException::class) {
            ServerWebInputException(it.toString())
        }
    }

}