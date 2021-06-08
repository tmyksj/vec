package vec.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.router
import vec.presentation.handler.HomeHandler

@Configuration
class RouterFunctionConfiguration(
    private val homeHandler: HomeHandler,
) {

    @Bean
    fun routerFunction(): RouterFunction<*> {
        return router {
            GET("/", homeHandler::index)
        }
    }

}
