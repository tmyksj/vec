package vec.presentation.controller.account

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
import reactor.kotlin.core.publisher.onErrorResume
import reactor.kotlin.core.publisher.toMono
import vec.domain.entity.User
import vec.presentation.form.account.EmailForm
import vec.useCase.command.ModifyUserEmailCommand

@Controller
class EmailController(
    private val modifyUserEmailCommand: ModifyUserEmailCommand,
) {

    @RequestMapping(method = [RequestMethod.GET], path = ["/account/email"])
    fun get(
        @AuthenticationPrincipal principal: User,
        emailForm: EmailForm,
    ): Mono<Rendering> {
        return Mono.fromCallable {
            Rendering.view("layout/default")
                .modelAttribute("principal", principal)
                .modelAttribute("template", "template/account/email")
                .status(HttpStatus.OK)
                .build()
        }
    }

    @RequestMapping(method = [RequestMethod.POST], path = ["/account/email"])
    fun post(
        @AuthenticationPrincipal principal: User,
        @Validated emailForm: EmailForm,
        bindingResult: BindingResult,
    ): Mono<Rendering> {
        return Mono.defer {
            if (bindingResult.hasErrors()) {
                throw ServerWebInputException(bindingResult.toString())
            }

            modifyUserEmailCommand.perform(
                ModifyUserEmailCommand.Request(
                    principal = principal,
                    email = checkNotNull(emailForm.email),
                )
            )
        }.map {
            Rendering.redirectTo("/account")
                .status(HttpStatus.SEE_OTHER)
                .build()
        }.onErrorResume(ServerWebInputException::class) {
            Rendering.view("layout/default")
                .modelAttribute("principal", principal)
                .modelAttribute("template", "template/account/email")
                .status(HttpStatus.BAD_REQUEST)
                .build()
                .toMono()
        }.onErrorResume(ModifyUserEmailCommand.EmailIsAlreadyInUseException::class) {
            bindingResult.rejectValue("email", "vec.presentation.form.account.EmailForm.email.alreadyInUse")

            Rendering.view("layout/default")
                .modelAttribute("principal", principal)
                .modelAttribute("template", "template/account/email")
                .status(HttpStatus.BAD_REQUEST)
                .build()
                .toMono()
        }
    }

}
