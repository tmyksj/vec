package vec.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.router
import vec.presentation.handler.HomeHandler
import vec.presentation.handler.ProductHandler

@Configuration
class RouterFunctionConfiguration(
    private val homeHandler: HomeHandler,
    private val productHandler: ProductHandler,
) {

    @Bean
    fun routerFunction(): RouterFunction<*> {
        return router {
            GET("/", homeHandler::index)
            GET("/product/{id}", productHandler::detail)
        }
    }

}
