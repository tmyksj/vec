package vec.configuration.bean

import org.flywaydb.core.Flyway
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment

@Configuration
class FlywayBean(
    private val environment: Environment,
) {

    @Bean(initMethod = "migrate")
    fun flyway(): Flyway {
        return Flyway(
            Flyway.configure()
                .baselineOnMigrate(true)
                .dataSource(
                    environment.getRequiredProperty("spring.r2dbc.url").replace("r2dbc", "jdbc"),
                    environment.getRequiredProperty("spring.r2dbc.username"),
                    environment.getRequiredProperty("spring.r2dbc.password"),
                )
        )
    }

}
