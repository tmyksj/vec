package vec.presentation.controller.signUp

import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.reactive.result.view.Rendering
import org.springframework.web.reactive.result.view.modelAttribute
import org.springframework.web.server.ServerWebInputException
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.onErrorResume
import reactor.kotlin.core.publisher.toMono
import vec.domain.entity.User
import vec.presentation.form.signUp.IndexForm
import vec.useCase.command.RegisterUserCommand

@Controller
class IndexController(
    private val registerUserCommand: RegisterUserCommand,
) {

    @RequestMapping(method = [RequestMethod.GET], path = ["/sign-up"])
    fun get(
        @AuthenticationPrincipal principal: User?,
        indexForm: IndexForm,
    ): Mono<Rendering> {
        if (principal != null) {
            return Mono.fromCallable {
                Rendering.redirectTo("/")
                    .build()
            }
        }

        return Mono.fromCallable {
            Rendering.view("layout/default")
                .modelAttribute("principal", null)
                .modelAttribute("template", "template/signUp/index")
                .status(HttpStatus.OK)
                .build()
        }
    }

    @RequestMapping(method = [RequestMethod.POST], path = ["/sign-up"])
    fun post(
        @AuthenticationPrincipal principal: User?,
        @Validated indexForm: IndexForm,
        bindingResult: BindingResult,
    ): Mono<Rendering> {
        if (principal != null) {
            return Mono.fromCallable {
                Rendering.redirectTo("/")
                    .build()
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
                RegisterUserCommand.Request(
                    principal = principal,
                    email = checkNotNull(indexForm.email),
                    passwordRaw = checkNotNull(indexForm.password),
                    hasRoleConsumer = true,
                )
            )
        }.map {
            Rendering.redirectTo("/sign-in")
                .status(HttpStatus.SEE_OTHER)
                .build()
        }.onErrorResume(ServerWebInputException::class) {
            Rendering.view("layout/default")
                .modelAttribute("principal", null)
                .modelAttribute("template", "template/signUp/index")
                .status(HttpStatus.BAD_REQUEST)
                .build()
                .toMono()
        }.onErrorResume(RegisterUserCommand.EmailIsAlreadyInUseException::class) {
            bindingResult.rejectValue("email", "vec.presentation.form.signUp.IndexForm.email.alreadyInUse")

            Rendering.view("layout/default")
                .modelAttribute("principal", null)
                .modelAttribute("template", "template/signUp/index")
                .status(HttpStatus.BAD_REQUEST)
                .build()
                .toMono()
        }
    }

}
