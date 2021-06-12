package vec.presentation.handler

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.validation.BeanPropertyBindingResult
import org.springframework.validation.BindingResult
import org.springframework.validation.Validator
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyToMono
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.server.ServerWebInputException
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import reactor.util.function.Tuple2
import vec.presentation.form.ProductForm
import vec.useCase.query.GetProductQuery

@Component
class ProductHandler(
    private val validator: Validator,
    private val getProductQuery: GetProductQuery,
) {

    fun detail(serverRequest: ServerRequest): Mono<ServerResponse> {
        val form: Mono<Tuple2<ProductForm.Detail, BindingResult>> =
            serverRequest.bodyToMono<ProductForm.Detail>()
                .defaultIfEmpty(ProductForm.Detail())
                .map {
                    it.copy(
                        id = serverRequest.pathVariable("id"),
                    )
                }.zipWhen {
                    BeanPropertyBindingResult(it, "form")
                        .apply { validator.validate(it, this) }
                        .toMono()
                }

        return form
            .doOnNext {
                if (it.t2.hasErrors()) {
                    throw ServerWebInputException(it.t2.toString())
                }
            }.flatMap {
                getProductQuery.perform(
                    GetProductQuery.Request(
                        id = checkNotNull(it.t1.id),
                    )
                )
            }.flatMap {
                ServerResponse.status(HttpStatus.OK)
                    .render(
                        "layout/default", mapOf(
                            "product" to it.product,
                            "template" to "template/product/detail",
                        )
                    )
            }.onErrorMap(ServerWebInputException::class.java) {
                ResponseStatusException(HttpStatus.NOT_FOUND)
            }.onErrorMap(GetProductQuery.NotFoundException::class.java) {
                ResponseStatusException(HttpStatus.NOT_FOUND)
            }
    }

}
