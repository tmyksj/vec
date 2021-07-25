package vec.presentation.controllerAdvice

import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import vec.presentation.component.RenderingComponent

@ControllerAdvice
class RenderingControllerAdvice(
    private val renderingComponent: RenderingComponent,
) {

    @ModelAttribute
    fun modelAttribute(
        serverWebExchange: ServerWebExchange,
        model: Model,
    ): Mono<Void> {
        return Mono.defer {
            renderingComponent.restoreRedirectAttributes(serverWebExchange)
        }.doOnNext {
            model.addAllAttributes(it)
        }.then()
    }

}
