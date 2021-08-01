package vec.integration.order

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.applyKt
import org.springframework.web.reactive.function.BodyInserters
import vec.domain.entity.Cart
import vec.domain.entity.User
import vec.factory.CartFactory
import vec.factory.CartProductFactory
import vec.factory.UserFactory

@SpringBootTest
class IndexTests {

    @Autowired
    private lateinit var applicationContext: ApplicationContext

    @Autowired
    private lateinit var cartFactory: CartFactory

    @Autowired
    private lateinit var cartProductFactory: CartProductFactory

    @Autowired
    private lateinit var userFactory: UserFactory

    @Test
    fun anonymous__get_responds_302() {
        WebTestClient.bindToApplicationContext(applicationContext)
            .applyKt(SecurityMockServerConfigurers.springSecurity())
            .build()
            .get().uri("/order")
            .exchange()
            .expectStatus().isFound
    }

    @Test
    fun anonymous__post_responds_302() {
        WebTestClient.bindToApplicationContext(applicationContext)
            .applyKt(SecurityMockServerConfigurers.springSecurity())
            .build()
            .mutateWith(SecurityMockServerConfigurers.csrf())
            .post().uri("/order")
            .body(
                BodyInserters.fromFormData("", "")
            ).exchange()
            .expectStatus().isFound
    }

    @Test
    fun role_admin__get_responds_403() {
        WebTestClient.bindToApplicationContext(applicationContext)
            .applyKt(SecurityMockServerConfigurers.springSecurity())
            .build()
            .mutateWith(SecurityMockServerConfigurers.mockUser(userFactory.create(hasRoleAdmin = true)))
            .get().uri("/order")
            .exchange()
            .expectStatus().isForbidden
    }

    @Test
    fun role_admin__post_responds_403() {
        WebTestClient.bindToApplicationContext(applicationContext)
            .applyKt(SecurityMockServerConfigurers.springSecurity())
            .build()
            .mutateWith(SecurityMockServerConfigurers.csrf())
            .mutateWith(SecurityMockServerConfigurers.mockUser(userFactory.create(hasRoleAdmin = true)))
            .post().uri("/order")
            .body(
                BodyInserters.fromFormData("", "")
            ).exchange()
            .expectStatus().isForbidden
    }

    @Test
    fun role_consumer__get_responds_200() {
        WebTestClient.bindToApplicationContext(applicationContext)
            .applyKt(SecurityMockServerConfigurers.springSecurity())
            .build()
            .mutateWith(SecurityMockServerConfigurers.mockUser(userFactory.create(hasRoleConsumer = true)))
            .get().uri("/order")
            .exchange()
            .expectStatus().isOk
    }

    @Test
    fun role_consumer__post_responds_303() {
        val user: User = userFactory.create(hasRoleConsumer = true)
        val cart: Cart = cartFactory.create(userId = user.id)
        cartProductFactory.create(cartId = cart.id)

        WebTestClient.bindToApplicationContext(applicationContext)
            .applyKt(SecurityMockServerConfigurers.springSecurity())
            .build()
            .mutateWith(SecurityMockServerConfigurers.csrf())
            .mutateWith(SecurityMockServerConfigurers.mockUser(user))
            .post().uri("/order")
            .body(
                BodyInserters.fromFormData("", "")
            ).exchange()
            .expectStatus().isSeeOther
    }

}
