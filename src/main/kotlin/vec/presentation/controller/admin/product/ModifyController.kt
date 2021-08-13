package vec.presentation.controller.admin.product

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
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.onErrorMap
import reactor.kotlin.core.publisher.onErrorResume
import vec.domain.entity.User
import vec.presentation.component.RenderingComponent
import vec.presentation.form.admin.product.ModifyForm
import vec.useCase.command.ModifyProductCommand
import vec.useCase.query.GetProductQuery

@Controller
class ModifyController(
    private val renderingComponent: RenderingComponent,
    private val modifyProductCommand: ModifyProductCommand,
    private val getProductQuery: GetProductQuery,
) {

    @RequestMapping(method = [RequestMethod.GET], path = ["/admin/product/{id}/modify"])
    fun get(
        serverWebExchange: ServerWebExchange,
        @AuthenticationPrincipal principal: User,
        modifyForm: ModifyForm,
    ): Mono<Rendering> {
        return Mono.defer {
            getProductQuery.perform(
                principal = principal,
                id = checkNotNull(modifyForm.id),
            )
        }.flatMap {
            renderingComponent.view("layout/default")
                .modelAttribute(
                    "modifyForm", modifyForm.copy(
                        id = it.id,
                        name = it.name,
                        description = it.description,
                        amount = it.amount,
                        taxRate = it.taxRate,
                        stock = 0L,
                    )
                )
                .modelAttribute("principal", principal)
                .modelAttribute("template", "template/admin/product/modify")
                .status(HttpStatus.OK)
                .build(serverWebExchange)
        }.onErrorMap(GetProductQuery.ProductIsNotFoundException::class) {
            ResponseStatusException(HttpStatus.NOT_FOUND)
        }
    }

    @RequestMapping(method = [RequestMethod.POST], path = ["/admin/product/{id}/modify"])
    fun post(
        serverWebExchange: ServerWebExchange,
        @AuthenticationPrincipal principal: User,
        @Validated modifyForm: ModifyForm,
        bindingResult: BindingResult,
    ): Mono<Rendering> {
        return Mono.defer {
            if (bindingResult.hasErrors()) {
                throw ServerWebInputException(bindingResult.toString())
            }

            modifyProductCommand.perform(
                principal = principal,
                id = checkNotNull(modifyForm.id),
                name = checkNotNull(modifyForm.name),
                description = checkNotNull(modifyForm.description),
                amount = checkNotNull(modifyForm.amount),
                taxRate = checkNotNull(modifyForm.taxRate),
                stock = checkNotNull(modifyForm.stock),
            )
        }.flatMap {
            renderingComponent.redirect("/admin/product/${it.id}")
                .status(HttpStatus.SEE_OTHER)
                .build(serverWebExchange)
        }.onErrorResume(ServerWebInputException::class) {
            renderingComponent.view("layout/default")
                .modelAttribute("principal", principal)
                .modelAttribute("template", "template/admin/product/modify")
                .status(HttpStatus.BAD_REQUEST)
                .build(serverWebExchange)
        }.onErrorMap(ModifyProductCommand.ProductIsNotFoundException::class) {
            ResponseStatusException(HttpStatus.BAD_REQUEST)
        }.onErrorMap(ModifyProductCommand.StockMustBeEnoughException::class) {
            ResponseStatusException(HttpStatus.BAD_REQUEST)
        }
    }

}
