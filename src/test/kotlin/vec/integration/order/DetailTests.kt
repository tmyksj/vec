package vec.integration.order

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.applyKt
import vec.domain.entity.Order
import vec.domain.entity.User
import vec.factory.OrderFactory
import vec.factory.UserFactory

@SpringBootTest
class DetailTests {

    @Autowired
    private lateinit var applicationContext: ApplicationContext

    @Autowired
    private lateinit var orderFactory: OrderFactory

    @Autowired
    private lateinit var userFactory: UserFactory

    @Test
    fun anonymous__get_responds_302() {
        val order: Order = orderFactory.create()

        WebTestClient.bindToApplicationContext(applicationContext)
            .applyKt(SecurityMockServerConfigurers.springSecurity())
            .build()
            .get().uri("/order/${order.id}")
            .exchange()
            .expectStatus().isFound
    }

    @Test
    fun role_admin__get_responds_403() {
        val user: User = userFactory.create(hasRoleAdmin = true)
        val order: Order = orderFactory.create(userId = user.id)

        WebTestClient.bindToApplicationContext(applicationContext)
            .applyKt(SecurityMockServerConfigurers.springSecurity())
            .build()
            .mutateWith(SecurityMockServerConfigurers.mockUser(user))
            .get().uri("/order/${order.id}")
            .exchange()
            .expectStatus().isForbidden
    }

    @Test
    fun role_consumer__get_responds_200() {
        val user: User = userFactory.create(hasRoleConsumer = true)
        val order: Order = orderFactory.create(userId = user.id)

        WebTestClient.bindToApplicationContext(applicationContext)
            .applyKt(SecurityMockServerConfigurers.springSecurity())
            .build()
            .mutateWith(SecurityMockServerConfigurers.mockUser(user))
            .get().uri("/order/${order.id}")
            .exchange()
            .expectStatus().isOk
    }

}
