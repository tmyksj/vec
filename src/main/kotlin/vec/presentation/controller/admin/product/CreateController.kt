package vec.presentation.controller.admin.product

import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.reactive.result.view.Rendering
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.ServerWebInputException
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.onErrorResume
import vec.domain.entity.User
import vec.presentation.component.RenderingComponent
import vec.presentation.form.admin.product.CreateForm
import vec.useCase.command.CreateProductCommand

@Controller
class CreateController(
    private val renderingComponent: RenderingComponent,
    private val createProductCommand: CreateProductCommand,
) {

    @RequestMapping(method = [RequestMethod.GET], path = ["/admin/product/create"])
    fun get(
        serverWebExchange: ServerWebExchange,
        @AuthenticationPrincipal principal: User,
        createForm: CreateForm,
    ): Mono<Rendering> {
        return Mono.defer {
            renderingComponent.view("layout/default")
                .modelAttribute("principal", principal)
                .modelAttribute("template", "template/admin/product/create")
                .status(HttpStatus.OK)
                .build(serverWebExchange)
        }
    }

    @RequestMapping(method = [RequestMethod.POST], path = ["/admin/product/create"])
    fun post(
        serverWebExchange: ServerWebExchange,
        @AuthenticationPrincipal principal: User,
        @Validated createForm: CreateForm,
        bindingResult: BindingResult,
    ): Mono<Rendering> {
        return Mono.defer {
            if (bindingResult.hasErrors()) {
                throw ServerWebInputException(bindingResult.toString())
            }

            createProductCommand.perform(
                principal = principal,
                name = checkNotNull(createForm.name),
                description = checkNotNull(createForm.description),
                amount = checkNotNull(createForm.amount),
                taxRate = checkNotNull(createForm.taxRate),
                stock = checkNotNull(createForm.stock),
            )
        }.flatMap {
            renderingComponent.redirect("/admin/product/${it.id}")
                .status(HttpStatus.SEE_OTHER)
                .build(serverWebExchange)
        }.onErrorResume(ServerWebInputException::class) {
            renderingComponent.view("layout/default")
                .modelAttribute("principal", principal)
                .modelAttribute("template", "template/admin/product/create")
                .status(HttpStatus.BAD_REQUEST)
                .build(serverWebExchange)
        }
    }

}
