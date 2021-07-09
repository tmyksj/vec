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
import vec.presentation.form.account.PasswordForm
import vec.useCase.command.ModifyUserPasswordCommand

@Controller
class PasswordController(
    private val modifyUserPasswordCommand: ModifyUserPasswordCommand,
) {

    @RequestMapping(method = [RequestMethod.GET], path = ["/account/password"])
    fun get(
        @AuthenticationPrincipal principal: User,
        passwordForm: PasswordForm,
    ): Mono<Rendering> {
        return Mono.fromCallable {
            Rendering.view("layout/default")
                .modelAttribute("principal", principal)
                .modelAttribute("template", "template/account/password")
                .status(HttpStatus.OK)
                .build()
        }
    }

    @RequestMapping(method = [RequestMethod.POST], path = ["/account/password"])
    fun post(
        @AuthenticationPrincipal principal: User,
        @Validated passwordForm: PasswordForm,
        bindingResult: BindingResult,
    ): Mono<Rendering> {
        return Mono.defer {
            if (passwordForm.newPassword != passwordForm.newPasswordConfirmation) {
                bindingResult.rejectValue(
                    "newPasswordConfirmation",
                    "vec.presentation.form.account.PasswordForm.newPasswordConfirmation.mismatch",
                )
            }

            if (bindingResult.hasErrors()) {
                throw ServerWebInputException(bindingResult.toString())
            }

            modifyUserPasswordCommand.perform(
                ModifyUserPasswordCommand.Request(
                    principal = principal,
                    currentPasswordRaw = checkNotNull(passwordForm.currentPassword),
                    newPasswordRaw = checkNotNull(passwordForm.newPassword),
                )
            )
        }.map {
            Rendering.redirectTo("/account")
                .status(HttpStatus.SEE_OTHER)
                .build()
        }.onErrorResume(ServerWebInputException::class) {
            Rendering.view("layout/default")
                .modelAttribute("principal", principal)
                .modelAttribute("template", "template/account/password")
                .status(HttpStatus.BAD_REQUEST)
                .build()
                .toMono()
        }.onErrorResume(ModifyUserPasswordCommand.PasswordMismatchesException::class) {
            bindingResult.rejectValue(
                "currentPassword",
                "vec.presentation.form.account.PasswordForm.currentPassword.mismatch",
            )

            Rendering.view("layout/default")
                .modelAttribute("principal", principal)
                .modelAttribute("template", "template/account/password")
                .status(HttpStatus.BAD_REQUEST)
                .build()
                .toMono()
        }
    }

}
