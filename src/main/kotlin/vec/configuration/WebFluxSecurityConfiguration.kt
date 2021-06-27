package vec.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebFluxSecurity
class WebFluxSecurityConfiguration {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }

    @Bean
    fun securityWebFilterChain(serverHttpSecurity: ServerHttpSecurity): SecurityWebFilterChain {
        serverHttpSecurity.authorizeExchange()
            .pathMatchers("/").permitAll()
            .pathMatchers("/cart/**").hasRole("CONSUMER")
            .pathMatchers("/css/**").permitAll()
            .pathMatchers("/js/**").permitAll()
            .pathMatchers("/product/**").permitAll()
            .pathMatchers("/sign-in/**").permitAll()
            .pathMatchers("/sign-up/**").permitAll()
            .anyExchange().denyAll()
        serverHttpSecurity.formLogin()
            .loginPage("/sign-in")
        serverHttpSecurity.logout()
            .logoutUrl("/sign-out")

        return serverHttpSecurity.build()
    }

}
