package vec.integration

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest
class HomeTests {

    @Autowired
    private lateinit var applicationContext: ApplicationContext

    @Test
    fun index_responds_200() {
        WebTestClient.bindToApplicationContext(applicationContext).build()
            .get()
            .uri("/")
            .exchange()
            .expectStatus().isOk
    }

}
