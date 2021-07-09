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

@SpringBootTest
class PasswordTests {

    @Autowired
    private lateinit var applicationContext: ApplicationContext

    @Autowired
    private lateinit var userFactory: UserFactory

    @Test
    fun anonymous__get_responds_302() {
        WebTestClient.bindToApplicationContext(applicationContext)
            .applyKt(SecurityMockServerConfigurers.springSecurity())
            .build()
            .get().uri("/account/password")
            .exchange()
            .expectStatus().isFound
    }

    @Test
    fun anonymous__post_responds_302() {
        WebTestClient.bindToApplicationContext(applicationContext)
            .applyKt(SecurityMockServerConfigurers.springSecurity())
            .build()
            .mutateWith(SecurityMockServerConfigurers.csrf())
            .post().uri("/account/password")
            .body(
                BodyInserters
                    .fromFormData("currentPassword", "password")
                    .with("newPassword", "newPassword")
                    .with("newPasswordConfirmation", "newPassword")
            ).exchange()
            .expectStatus().isFound
    }

    @Test
    fun role_admin__get_responds_200() {
        WebTestClient.bindToApplicationContext(applicationContext)
            .applyKt(SecurityMockServerConfigurers.springSecurity())
            .build()
            .mutateWith(SecurityMockServerConfigurers.mockUser(userFactory.create(hasRoleAdmin = true)))
            .get().uri("/account/password")
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
            .post().uri("/account/password")
            .body(
                BodyInserters
                    .fromFormData("currentPassword", "password")
                    .with("newPassword", "newPassword")
                    .with("newPasswordConfirmation", "newPassword")
            ).exchange()
            .expectStatus().isSeeOther
    }

    @Test
    fun role_admin__post_responds_400_when_current_password_does_not_match() {
        WebTestClient.bindToApplicationContext(applicationContext)
            .applyKt(SecurityMockServerConfigurers.springSecurity())
            .build()
            .mutateWith(SecurityMockServerConfigurers.csrf())
            .mutateWith(SecurityMockServerConfigurers.mockUser(userFactory.create(hasRoleAdmin = true)))
            .post().uri("/account/password")
            .body(
                BodyInserters
                    .fromFormData("currentPassword", "currentPassword")
                    .with("newPassword", "newPassword")
                    .with("newPasswordConfirmation", "newPassword")
            ).exchange()
            .expectStatus().isBadRequest
    }

    @Test
    fun role_admin__post_responds_400_when_new_password_does_not_match() {
        WebTestClient.bindToApplicationContext(applicationContext)
            .applyKt(SecurityMockServerConfigurers.springSecurity())
            .build()
            .mutateWith(SecurityMockServerConfigurers.csrf())
            .mutateWith(SecurityMockServerConfigurers.mockUser(userFactory.create(hasRoleAdmin = true)))
            .post().uri("/account/password")
            .body(
                BodyInserters
                    .fromFormData("currentPassword", "password")
                    .with("newPassword", "newPassword")
                    .with("newPasswordConfirmation", "newPasswordConfirmation")
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
            .post().uri("/account/password")
            .body(
                BodyInserters
                    .fromFormData("currentPassword", "password")
                    .with("newPassword", "pass")
                    .with("newPasswordConfirmation", "pass")
            ).exchange()
            .expectStatus().isBadRequest
    }

    @Test
    fun role_consumer__get_responds_200() {
        WebTestClient.bindToApplicationContext(applicationContext)
            .applyKt(SecurityMockServerConfigurers.springSecurity())
            .build()
            .mutateWith(SecurityMockServerConfigurers.mockUser(userFactory.create(hasRoleConsumer = true)))
            .get().uri("/account/password")
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
            .post().uri("/account/password")
            .body(
                BodyInserters
                    .fromFormData("currentPassword", "password")
                    .with("newPassword", "newPassword")
                    .with("newPasswordConfirmation", "newPassword")
            ).exchange()
            .expectStatus().isSeeOther
    }

    @Test
    fun role_consumer__post_responds_400_when_current_password_does_not_match() {
        WebTestClient.bindToApplicationContext(applicationContext)
            .applyKt(SecurityMockServerConfigurers.springSecurity())
            .build()
            .mutateWith(SecurityMockServerConfigurers.csrf())
            .mutateWith(SecurityMockServerConfigurers.mockUser(userFactory.create(hasRoleConsumer = true)))
            .post().uri("/account/password")
            .body(
                BodyInserters
                    .fromFormData("currentPassword", "currentPassword")
                    .with("newPassword", "newPassword")
                    .with("newPasswordConfirmation", "newPassword")
            ).exchange()
            .expectStatus().isBadRequest
    }

    @Test
    fun role_consumer__post_responds_400_when_new_password_does_not_match() {
        WebTestClient.bindToApplicationContext(applicationContext)
            .applyKt(SecurityMockServerConfigurers.springSecurity())
            .build()
            .mutateWith(SecurityMockServerConfigurers.csrf())
            .mutateWith(SecurityMockServerConfigurers.mockUser(userFactory.create(hasRoleConsumer = true)))
            .post().uri("/account/password")
            .body(
                BodyInserters
                    .fromFormData("currentPassword", "password")
                    .with("newPassword", "newPassword")
                    .with("newPasswordConfirmation", "newPasswordConfirmation")
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
            .post().uri("/account/password")
            .body(
                BodyInserters
                    .fromFormData("currentPassword", "password")
                    .with("newPassword", "pass")
                    .with("newPasswordConfirmation", "pass")
            ).exchange()
            .expectStatus().isBadRequest
    }

}
