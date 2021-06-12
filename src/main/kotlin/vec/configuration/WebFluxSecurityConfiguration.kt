package vec.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebFluxSecurity
class WebFluxSecurityConfiguration {

    @Bean
    fun securityWebFilterChain(serverHttpSecurity: ServerHttpSecurity): SecurityWebFilterChain {
        serverHttpSecurity.authorizeExchange()
            .pathMatchers("/").permitAll()
            .pathMatchers("/css/**").permitAll()
            .pathMatchers("/product/**").permitAll()

        return serverHttpSecurity.build()
    }

}
