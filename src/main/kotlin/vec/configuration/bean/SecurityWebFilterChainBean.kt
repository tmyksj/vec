package vec.configuration.bean

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
class SecurityWebFilterChainBean {

    @Bean
    fun securityWebFilterChain(serverHttpSecurity: ServerHttpSecurity): SecurityWebFilterChain {
        serverHttpSecurity.authorizeExchange()
            .pathMatchers("/").permitAll()
            .pathMatchers("/account/**").hasAnyRole("ADMIN", "CONSUMER")
            .pathMatchers("/account-delete/**").permitAll()
            .pathMatchers("/cart/**").hasAnyRole("CONSUMER")
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
