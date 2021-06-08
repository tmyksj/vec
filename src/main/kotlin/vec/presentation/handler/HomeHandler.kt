package vec.presentation.handler

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import vec.useCase.query.GetInformationQuery

@Component
class HomeHandler(
    private val getInformationQuery: GetInformationQuery,
) {

    fun index(serverRequest: ServerRequest): Mono<ServerResponse> {
        return getInformationQuery.perform(GetInformationQuery.Request())
            .flatMap {
                ServerResponse.ok()
                    .render(
                        "layout/default", mapOf(
                            "productList" to it.productList,
                            "template" to "template/home/index",
                        )
                    )
            }
    }

}
