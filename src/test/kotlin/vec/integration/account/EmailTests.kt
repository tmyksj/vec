package vec.integration.account

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.applyKt
import org.springframework.web.reactive.function.BodyInserters
import vec.factory.UserFactory
import java.util.*

@SpringBootTest
class EmailTests {

    @Autowired
    private lateinit var applicationContext: ApplicationContext

    @Autowired
    private lateinit var userFactory: UserFactory

    @Test
    fun anonymous__get_responds_302() {
        WebTestClient.bindToApplicationContext(applicationContext)
            .applyKt(SecurityMockServerConfigurers.springSecurity())
            .build()
            .get().uri("/account/email")
            .exchange()
            .expectStatus().isFound
    }

    @Test
    fun anonymous__post_responds_302() {
        WebTestClient.bindToApplicationContext(applicationContext)
            .applyKt(SecurityMockServerConfigurers.springSecurity())
            .build()
            .mutateWith(SecurityMockServerConfigurers.csrf())
            .post().uri("/account/email")
            .body(
                BodyInserters
                    .fromFormData("email", "${UUID.randomUUID()}@example.com")
            ).exchange()
            .expectStatus().isFound
    }

    @Test
    fun role_admin__get_responds_200() {
        WebTestClient.bindToApplicationContext(applicationContext)
            .applyKt(SecurityMockServerConfigurers.springSecurity())
            .build()
            .mutateWith(SecurityMockServerConfigurers.mockUser(userFactory.create(hasRoleAdmin = true)))
            .get().uri("/account/email")
            .exchange()
            .expectStatus().isOk
    }

    @Test
    fun role_admin__post_responds_303() {
        WebTestClient.bindToApplicationContext(applicationContext)
            .applyKt(SecurityMockServerConfigurers.springSecurity())
            .build()
            .mutateWith(SecurityMockServerConfigurers.csrf())
            .mutateWith(SecurityMockServerConfigurers.mockUser(userFactory.create(hasRoleAdmin = true)))
            .post().uri("/account/email")
            .body(
                BodyInserters
                    .fromFormData("email", "${UUID.randomUUID()}@example.com")
            ).exchange()
            .expectStatus().isSeeOther
    }

    @Test
    fun role_admin__post_responds_400_when_email_is_already_in_use() {
        WebTestClient.bindToApplicationContext(applicationContext)
            .applyKt(SecurityMockServerConfigurers.springSecurity())
            .build()
            .mutateWith(SecurityMockServerConfigurers.csrf())
            .mutateWith(SecurityMockServerConfigurers.mockUser(userFactory.create(hasRoleAdmin = true)))
            .post().uri("/account/email")
            .body(
                BodyInserters
                    .fromFormData("email", userFactory.create().email)
            ).exchange()
            .expectStatus().isBadRequest
    }

    @Test
    fun role_admin__post_responds_400_when_params_are_invalid() {
        WebTestClient.bindToApplicationContext(applicationContext)
            .applyKt(SecurityMockServerConfigurers.springSecurity())
            .build()
            .mutateWith(SecurityMockServerConfigurers.csrf())
            .mutateWith(SecurityMockServerConfigurers.mockUser(userFactory.create(hasRoleAdmin = true)))
            .post().uri("/account/email")
            .body(
                BodyInserters
                    .fromFormData("email", "[invalid]email")
            ).exchange()
            .expectStatus().isBadRequest
    }

    @Test
    fun role_consumer__get_responds_200() {
        WebTestClient.bindToApplicationContext(applicationContext)
            .applyKt(SecurityMockServerConfigurers.springSecurity())
            .build()
            .mutateWith(SecurityMockServerConfigurers.mockUser(userFactory.create(hasRoleConsumer = true)))
            .get().uri("/account/email")
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
            .post().uri("/account/email")
            .body(
                BodyInserters
                    .fromFormData("email", "${UUID.randomUUID()}@example.com")
            ).exchange()
            .expectStatus().isSeeOther
    }

    @Test
    fun role_consumer__post_responds_400_when_email_is_already_in_use() {
        WebTestClient.bindToApplicationContext(applicationContext)
            .applyKt(SecurityMockServerConfigurers.springSecurity())
            .build()
            .mutateWith(SecurityMockServerConfigurers.csrf())
            .mutateWith(SecurityMockServerConfigurers.mockUser(userFactory.create(hasRoleConsumer = true)))
            .post().uri("/account/email")
            .body(
                BodyInserters
                    .fromFormData("email", userFactory.create().email)
            ).exchange()
            .expectStatus().isBadRequest
    }

    @Test
    fun role_consumer__post_responds_400_when_params_are_invalid() {
        WebTestClient.bindToApplicationContext(applicationContext)
            .applyKt(SecurityMockServerConfigurers.springSecurity())
            .build()
            .mutateWith(SecurityMockServerConfigurers.csrf())
            .mutateWith(SecurityMockServerConfigurers.mockUser(userFactory.create(hasRoleConsumer = true)))
            .post().uri("/account/email")
            .body(
                BodyInserters
                    .fromFormData("email", "[invalid]email")
            ).exchange()
            .expectStatus().isBadRequest
    }

}
