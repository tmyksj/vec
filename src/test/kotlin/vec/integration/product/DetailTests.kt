package vec.integration.product

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.applyKt
import vec.factory.ProductFactory
import java.util.*

@SpringBootTest
class DetailTests {

    @Autowired
    private lateinit var applicationContext: ApplicationContext

    @Autowired
    private lateinit var productFactory: ProductFactory

    @Test
    fun get_responds_200() {
        WebTestClient.bindToApplicationContext(applicationContext)
            .applyKt(SecurityMockServerConfigurers.springSecurity())
            .build()
            .get().uri("/product/${productFactory.create().id}")
            .exchange()
            .expectStatus().isOk
    }

    @Test
    fun get_responds_404_when_product_does_not_exist() {
        WebTestClient.bindToApplicationContext(applicationContext)
            .applyKt(SecurityMockServerConfigurers.springSecurity())
            .build()
            .get().uri("/product/${UUID.randomUUID()}")
            .exchange()
            .expectStatus().isNotFound
    }

}
