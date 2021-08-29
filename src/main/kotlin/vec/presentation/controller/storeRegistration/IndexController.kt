package vec.presentation.controller.storeRegistration

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
import vec.presentation.form.storeRegistration.IndexForm
import vec.useCase.command.RegisterStoreCommand

@Controller
class IndexController(
    private val renderingComponent: RenderingComponent,
    private val registerStoreCommand: RegisterStoreCommand,
) {

    @RequestMapping(method = [RequestMethod.GET], path = ["/store-registration"])
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
                .modelAttribute("template", "template/storeRegistration/index")
                .status(HttpStatus.OK)
                .build(serverWebExchange)
        }
    }

    @RequestMapping(method = [RequestMethod.POST], path = ["/store-registration"])
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
                    "passwordConfirmation",
                    "vec.presentation.form.storeRegistration.IndexForm.passwordConfirmation.mismatch",
                )
            }

            if (bindingResult.hasErrors()) {
                throw ServerWebInputException(bindingResult.toString())
            }

            registerStoreCommand.perform(
                principal = principal,
                name = checkNotNull(indexForm.name),
                address = checkNotNull(indexForm.address),
                email = checkNotNull(indexForm.email),
                passwordRaw = checkNotNull(indexForm.password),
            )
        }.flatMap {
            renderingComponent.redirect("/sign-in")
                .redirectAttribute("info", "vec.presentation.controller.storeRegistration.IndexController.post.ok")
                .status(HttpStatus.SEE_OTHER)
                .build(serverWebExchange)
        }.onErrorResume(ServerWebInputException::class) {
            renderingComponent.view("layout/default")
                .modelAttribute("principal", null)
                .modelAttribute("template", "template/storeRegistration/index")
                .status(HttpStatus.BAD_REQUEST)
                .build(serverWebExchange)
        }.onErrorResume(RegisterStoreCommand.EmailIsAlreadyInUseException::class) {
            bindingResult.rejectValue("email", "vec.presentation.form.storeRegistration.IndexForm.email.alreadyInUse")

            renderingComponent.view("layout/default")
                .modelAttribute("principal", null)
                .modelAttribute("template", "template/storeRegistration/index")
                .status(HttpStatus.BAD_REQUEST)
                .build(serverWebExchange)
        }
    }

}
