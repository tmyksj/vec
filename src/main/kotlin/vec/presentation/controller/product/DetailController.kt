package vec.presentation.controller.product

import org.springframework.http.HttpStatus
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.reactive.result.view.Rendering
import org.springframework.web.reactive.result.view.modelAttribute
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.server.ServerWebInputException
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.onErrorMap
import vec.domain.entity.User
import vec.presentation.form.product.DetailForm
import vec.useCase.query.GetProductQuery

@Controller
class DetailController(
    private val getProductQuery: GetProductQuery,
) {

    @RequestMapping(method = [RequestMethod.GET], path = ["/product/{id}"])
    fun get(
        @AuthenticationPrincipal principal: User?,
        @Validated detailForm: DetailForm,
        bindingResult: BindingResult,
    ): Mono<Rendering> {
        return Mono.defer {
            if (bindingResult.hasErrors()) {
                throw ServerWebInputException(bindingResult.toString())
            }

            getProductQuery.perform(
                GetProductQuery.Request(
                    principal = principal,
                    id = checkNotNull(detailForm.id),
                )
            )
        }.map {
            Rendering.view("layout/default")
                .modelAttribute("principal", principal)
                .modelAttribute("product", it.product)
                .modelAttribute("template", "template/product/detail")
                .status(HttpStatus.OK)
                .build()
        }.onErrorMap(ServerWebInputException::class) {
            ResponseStatusException(HttpStatus.NOT_FOUND)
        }.onErrorMap(GetProductQuery.ProductIsNotFoundException::class) {
            ResponseStatusException(HttpStatus.NOT_FOUND)
        }
    }

}
