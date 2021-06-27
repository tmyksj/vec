package vec.integration.cart

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.applyKt
import org.springframework.web.reactive.function.BodyInserters
import vec.factory.ProductFactory
import vec.factory.UserFactory
import java.util.*

@SpringBootTest
class IndexTests {

    @Autowired
    private lateinit var applicationContext: ApplicationContext

    @Autowired
    private lateinit var productFactory: ProductFactory

    @Autowired
    private lateinit var userFactory: UserFactory

    @Test
    fun anonymous__get_responds_302() {
        WebTestClient.bindToApplicationContext(applicationContext)
            .applyKt(SecurityMockServerConfigurers.springSecurity())
            .build()
            .get().uri("/cart")
            .exchange()
            .expectStatus().isFound
    }

    @Test
    fun anonymous__post_responds_302() {
        WebTestClient.bindToApplicationContext(applicationContext)
            .applyKt(SecurityMockServerConfigurers.springSecurity())
            .build()
            .mutateWith(SecurityMockServerConfigurers.csrf())
            .post().uri("/cart")
            .body(
                BodyInserters
                    .fromFormData("productId", productFactory.create().id)
            ).exchange()
            .expectStatus().isFound
    }

    @Test
    fun role_admin__get_responds_403() {
        WebTestClient.bindToApplicationContext(applicationContext)
            .applyKt(SecurityMockServerConfigurers.springSecurity())
            .build()
            .mutateWith(SecurityMockServerConfigurers.mockUser(userFactory.create(hasRoleAdmin = true)))
            .get().uri("/cart")
            .exchange()
            .expectStatus().isForbidden
    }

    @Test
    fun role_admin__post_responds_403() {
        WebTestClient.bindToApplicationContext(applicationContext)
            .applyKt(SecurityMockServerConfigurers.springSecurity())
            .build()
            .mutateWith(SecurityMockServerConfigurers.mockUser(userFactory.create(hasRoleAdmin = true)))
            .mutateWith(SecurityMockServerConfigurers.csrf())
            .post().uri("/cart")
            .body(
                BodyInserters
                    .fromFormData("productId", productFactory.create().id)
            ).exchange()
            .expectStatus().isForbidden
    }

    @Test
    fun role_consumer__get_responds_200() {
        WebTestClient.bindToApplicationContext(applicationContext)
            .applyKt(SecurityMockServerConfigurers.springSecurity())
            .build()
            .mutateWith(SecurityMockServerConfigurers.mockUser(userFactory.create(hasRoleConsumer = true)))
            .get().uri("/cart")
            .exchange()
            .expectStatus().isOk
    }

    @Test
    fun role_consumer__post_responds_303() {
        WebTestClient.bindToApplicationContext(applicationContext)
            .applyKt(SecurityMockServerConfigurers.springSecurity())
            .build()
            .mutateWith(SecurityMockServerConfigurers.csrf())
            .mutateWith(SecurityMockServerConfigurers.mockUser(userFactory.create(hasRoleConsumer = true)))
            .post().uri("/cart")
            .body(
                BodyInserters
                    .fromFormData("productId", productFactory.create().id)
            ).exchange()
            .expectStatus().isSeeOther
    }

    @Test
    fun role_consumer__post_responds_400_when_product_does_not_exist() {
        WebTestClient.bindToApplicationContext(applicationContext)
            .applyKt(SecurityMockServerConfigurers.springSecurity())
            .build()
            .mutateWith(SecurityMockServerConfigurers.csrf())
            .mutateWith(SecurityMockServerConfigurers.mockUser(userFactory.create(hasRoleConsumer = true)))
            .post().uri("/cart")
            .body(
                BodyInserters
                    .fromFormData("productId", UUID.randomUUID().toString())
            ).exchange()
            .expectStatus().isBadRequest
    }

}
