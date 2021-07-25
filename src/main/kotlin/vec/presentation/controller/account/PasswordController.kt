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
import vec.presentation.form.account.PasswordForm
import vec.useCase.command.ModifyUserPasswordCommand
import vec.useCase.service.PrincipalService

@Controller
class PasswordController(
    private val renderingComponent: RenderingComponent,
    private val modifyUserPasswordCommand: ModifyUserPasswordCommand,
    private val principalService: PrincipalService,
) {

    @RequestMapping(method = [RequestMethod.GET], path = ["/account/password"])
    fun get(
        serverWebExchange: ServerWebExchange,
        @AuthenticationPrincipal principal: User,
        passwordForm: PasswordForm,
    ): Mono<Rendering> {
        return Mono.defer {
            renderingComponent.view("layout/default")
                .modelAttribute("principal", principal)
                .modelAttribute("template", "template/account/password")
                .status(HttpStatus.OK)
                .build(serverWebExchange)
        }
    }

    @RequestMapping(method = [RequestMethod.POST], path = ["/account/password"])
    fun post(
        serverWebExchange: ServerWebExchange,
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
                principal = principal,
                currentPasswordRaw = checkNotNull(passwordForm.currentPassword),
                newPasswordRaw = checkNotNull(passwordForm.newPassword),
            )
        }.flatMap {
            principalService.reload(serverWebExchange)
        }.flatMap {
            renderingComponent.redirect("/account")
                .status(HttpStatus.SEE_OTHER)
                .build(serverWebExchange)
        }.onErrorResume(ServerWebInputException::class) {
            renderingComponent.view("layout/default")
                .modelAttribute("principal", principal)
                .modelAttribute("template", "template/account/password")
                .status(HttpStatus.BAD_REQUEST)
                .build(serverWebExchange)
        }.onErrorResume(ModifyUserPasswordCommand.PasswordMismatchesException::class) {
            bindingResult.rejectValue(
                "currentPassword",
                "vec.presentation.form.account.PasswordForm.currentPassword.mismatch",
            )

            renderingComponent.view("layout/default")
                .modelAttribute("principal", principal)
                .modelAttribute("template", "template/account/password")
                .status(HttpStatus.BAD_REQUEST)
                .build(serverWebExchange)
        }
    }

}
