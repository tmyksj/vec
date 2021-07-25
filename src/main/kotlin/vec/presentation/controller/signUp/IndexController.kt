package vec.presentation.controller.signUp

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
import vec.presentation.form.signUp.IndexForm
import vec.useCase.command.RegisterUserCommand

@Controller
class IndexController(
    private val renderingComponent: RenderingComponent,
    private val registerUserCommand: RegisterUserCommand,
) {

    @RequestMapping(method = [RequestMethod.GET], path = ["/sign-up"])
    fun get(
        serverWebExchange: ServerWebExchange,
        @AuthenticationPrincipal principal: User?,
        indexForm: IndexForm,
    ): Mono<Rendering> {
        if (principal != null) {
            return Mono.defer {
                renderingComponent.redirect("/")
                    .status(HttpStatus.SEE_OTHER)
                    .build(serverWebExchange)
            }
        }

        return Mono.defer {
            renderingComponent.view("layout/default")
                .modelAttribute("principal", null)
                .modelAttribute("template", "template/signUp/index")
                .status(HttpStatus.OK)
                .build(serverWebExchange)
        }
    }

    @RequestMapping(method = [RequestMethod.POST], path = ["/sign-up"])
    fun post(
        serverWebExchange: ServerWebExchange,
        @AuthenticationPrincipal principal: User?,
        @Validated indexForm: IndexForm,
        bindingResult: BindingResult,
    ): Mono<Rendering> {
        if (principal != null) {
            return Mono.defer {
                renderingComponent.redirect("/")
                    .status(HttpStatus.SEE_OTHER)
                    .build(serverWebExchange)
            }
        }

        return Mono.defer {
            if (indexForm.password != indexForm.passwordConfirmation) {
                bindingResult.rejectValue(
                    "passwordConfirmation", "vec.presentation.form.signUp.IndexForm.passwordConfirmation.mismatch"
                )
            }

            if (bindingResult.hasErrors()) {
                throw ServerWebInputException(bindingResult.toString())
            }

            registerUserCommand.perform(
                principal = principal,
                email = checkNotNull(indexForm.email),
                passwordRaw = checkNotNull(indexForm.password),
                hasRoleConsumer = true,
            )
        }.flatMap {
            renderingComponent.redirect("/sign-in")
                .status(HttpStatus.SEE_OTHER)
                .build(serverWebExchange)
        }.onErrorResume(ServerWebInputException::class) {
            renderingComponent.view("layout/default")
                .modelAttribute("principal", null)
                .modelAttribute("template", "template/signUp/index")
                .status(HttpStatus.BAD_REQUEST)
                .build(serverWebExchange)
        }.onErrorResume(RegisterUserCommand.EmailIsAlreadyInUseException::class) {
            bindingResult.rejectValue("email", "vec.presentation.form.signUp.IndexForm.email.alreadyInUse")

            renderingComponent.view("layout/default")
                .modelAttribute("principal", null)
                .modelAttribute("template", "template/signUp/index")
                .status(HttpStatus.BAD_REQUEST)
                .build(serverWebExchange)
        }
    }

}
