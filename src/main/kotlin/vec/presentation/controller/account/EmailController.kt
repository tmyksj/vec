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
import vec.presentation.form.account.EmailForm
import vec.useCase.command.ModifyUserEmailCommand
import vec.useCase.service.PrincipalService

@Controller
class EmailController(
    private val renderingComponent: RenderingComponent,
    private val modifyUserEmailCommand: ModifyUserEmailCommand,
    private val principalService: PrincipalService,
) {

    @RequestMapping(method = [RequestMethod.GET], path = ["/account/email"])
    fun get(
        serverWebExchange: ServerWebExchange,
        @AuthenticationPrincipal principal: User,
        emailForm: EmailForm,
    ): Mono<Rendering> {
        return Mono.defer {
            renderingComponent.view("layout/default")
                .modelAttribute("emailForm", emailForm.copy(email = principal.email))
                .modelAttribute("principal", principal)
                .modelAttribute("template", "template/account/email")
                .status(HttpStatus.OK)
                .build(serverWebExchange)
        }
    }

    @RequestMapping(method = [RequestMethod.POST], path = ["/account/email"])
    fun post(
        serverWebExchange: ServerWebExchange,
        @AuthenticationPrincipal principal: User,
        @Validated emailForm: EmailForm,
        bindingResult: BindingResult,
    ): Mono<Rendering> {
        return Mono.defer {
            if (bindingResult.hasErrors()) {
                throw ServerWebInputException(bindingResult.toString())
            }

            modifyUserEmailCommand.perform(
                principal = principal,
                email = checkNotNull(emailForm.email),
            )
        }.flatMap {
            principalService.reload(serverWebExchange)
        }.flatMap {
            renderingComponent.redirect("/account")
                .redirectAttribute("info", "vec.presentation.controller.account.EmailController.post.ok")
                .status(HttpStatus.SEE_OTHER)
                .build(serverWebExchange)
        }.onErrorResume(ServerWebInputException::class) {
            renderingComponent.view("layout/default")
                .modelAttribute("principal", principal)
                .modelAttribute("template", "template/account/email")
                .status(HttpStatus.BAD_REQUEST)
                .build(serverWebExchange)
        }.onErrorResume(ModifyUserEmailCommand.EmailIsAlreadyInUseException::class) {
            bindingResult.rejectValue("email", "vec.presentation.form.account.EmailForm.email.alreadyInUse")

            renderingComponent.view("layout/default")
                .modelAttribute("principal", principal)
                .modelAttribute("template", "template/account/email")
                .status(HttpStatus.BAD_REQUEST)
                .build(serverWebExchange)
        }
    }

}
