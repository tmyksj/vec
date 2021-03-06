package vec.presentation.controller.account

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
import vec.presentation.form.account.DeleteForm
import vec.useCase.command.UnregisterUserCommand
import vec.useCase.service.PrincipalService

@Controller
class DeleteController(
    private val renderingComponent: RenderingComponent,
    private val unregisterUserCommand: UnregisterUserCommand,
    private val principalService: PrincipalService,
) {

    @RequestMapping(method = [RequestMethod.GET], path = ["/account/delete"])
    fun get(
        serverWebExchange: ServerWebExchange,
        @AuthenticationPrincipal principal: User,
        deleteForm: DeleteForm,
    ): Mono<Rendering> {
        return Mono.defer {
            renderingComponent.view("layout/default")
                .modelAttribute("principal", principal)
                .modelAttribute("template", "template/account/delete")
                .status(HttpStatus.OK)
                .build(serverWebExchange)
        }
    }

    @RequestMapping(method = [RequestMethod.POST], path = ["/account/delete"])
    fun post(
        serverWebExchange: ServerWebExchange,
        @AuthenticationPrincipal principal: User,
        @Validated deleteForm: DeleteForm,
        bindingResult: BindingResult,
    ): Mono<Rendering> {
        return Mono.defer {
            if (bindingResult.hasErrors()) {
                throw ServerWebInputException(bindingResult.toString())
            }

            unregisterUserCommand.perform(
                principal = principal,
            )
        }.flatMap {
            principalService.clear(serverWebExchange)
        }.flatMap {
            renderingComponent.redirect("/account-delete")
                .status(HttpStatus.SEE_OTHER)
                .build(serverWebExchange)
        }.onErrorResume(ServerWebInputException::class) {
            renderingComponent.view("layout/default")
                .modelAttribute("principal", principal)
                .modelAttribute("template", "template/account/delete")
                .status(HttpStatus.BAD_REQUEST)
                .build(serverWebExchange)
        }
    }

}
