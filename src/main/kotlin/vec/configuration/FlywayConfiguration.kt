package vec.configuration

import org.flywaydb.core.Flyway
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment

@Configuration
class FlywayConfiguration(
    private val environment: Environment,
) {

    @Bean(initMethod = "migrate")
    fun flyway(): Flyway {
        return Flyway(
            Flyway.configure()
                .baselineOnMigrate(true)
                .dataSource(
                    environment.getRequiredProperty("spring.flyway.url"),
                    environment.getRequiredProperty("spring.flyway.user"),
                    environment.getRequiredProperty("spring.flyway.password"),
                )
        )
    }

}
