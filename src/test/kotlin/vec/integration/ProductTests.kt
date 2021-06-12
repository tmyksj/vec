package vec.integration

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.web.reactive.server.WebTestClient
import vec.factory.ProductFactory
import java.util.*

@SpringBootTest
class ProductTests {

    @Autowired
    private lateinit var applicationContext: ApplicationContext

    @Autowired
    private lateinit var productFactory: ProductFactory

    @Test
    fun detail_responds_200() {
        WebTestClient.bindToApplicationContext(applicationContext).build()
            .get()
            .uri("/product/${productFactory.save().id}")
            .exchange()
            .expectStatus().isOk
    }

    @Test
    fun detail_responds_404_when_product_does_not_exist() {
        WebTestClient.bindToApplicationContext(applicationContext).build()
            .get()
            .uri("/product/${UUID.randomUUID()}")
            .exchange()
            .expectStatus().isNotFound
    }

}
