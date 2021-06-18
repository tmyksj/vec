package vec.integration.signUp

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
class IndexTests {

    @Autowired
    private lateinit var applicationContext: ApplicationContext

    @Autowired
    private lateinit var userFactory: UserFactory

    @Test
    fun get_responds_200() {
        WebTestClient.bindToApplicationContext(applicationContext)
            .applyKt(SecurityMockServerConfigurers.springSecurity())
            .build()
            .get().uri("/sign-up")
            .exchange()
            .expectStatus().isOk
    }

    @Test
    fun get_responds_303_when_user_signed_in() {
        WebTestClient.bindToApplicationContext(applicationContext)
            .applyKt(SecurityMockServerConfigurers.springSecurity())
            .build()
            .mutateWith(SecurityMockServerConfigurers.mockUser(userFactory.create()))
            .get().uri("/sign-up")
            .exchange()
            .expectStatus().isSeeOther
    }

    @Test
    fun post_responds_303() {
        WebTestClient.bindToApplicationContext(applicationContext)
            .applyKt(SecurityMockServerConfigurers.springSecurity())
            .build()
            .mutateWith(SecurityMockServerConfigurers.csrf())
            .post().uri("/sign-up")
            .body(
                BodyInserters
                    .fromFormData("email", "${UUID.randomUUID()}@example.com")
                    .with("password", "passwordRaw")
                    .with("passwordConfirmation", "passwordRaw")
            ).exchange()
            .expectStatus().isSeeOther
    }

    @Test
    fun post_responds_303_when_user_signed_in() {
        WebTestClient.bindToApplicationContext(applicationContext)
            .applyKt(SecurityMockServerConfigurers.springSecurity())
            .build()
            .mutateWith(SecurityMockServerConfigurers.csrf())
            .mutateWith(SecurityMockServerConfigurers.mockUser(userFactory.create()))
            .post().uri("/sign-up")
            .body(
                BodyInserters
                    .fromFormData("email", "${UUID.randomUUID()}@example.com")
                    .with("password", "passwordRaw")
                    .with("passwordConfirmation", "passwordRaw")
            ).exchange()
            .expectStatus().isSeeOther
    }

    @Test
    fun post_responds_400_when_email_is_already_in_use() {
        WebTestClient.bindToApplicationContext(applicationContext)
            .applyKt(SecurityMockServerConfigurers.springSecurity())
            .build()
            .mutateWith(SecurityMockServerConfigurers.csrf())
            .post().uri("/sign-up")
            .body(
                BodyInserters
                    .fromFormData("email", userFactory.create().email)
                    .with("password", "passwordRaw")
                    .with("passwordConfirmation", "passwordRaw")
            ).exchange()
            .expectStatus().isBadRequest
    }

    @Test
    fun post_responds_400_when_params_are_invalid() {
        WebTestClient.bindToApplicationContext(applicationContext)
            .applyKt(SecurityMockServerConfigurers.springSecurity())
            .build()
            .mutateWith(SecurityMockServerConfigurers.csrf())
            .post().uri("/sign-up")
            .body(
                BodyInserters
                    .fromFormData("email", "[invalid]email")
                    .with("password", "passwordRaw")
                    .with("passwordConfirmation", "passwordRaw")
            ).exchange()
            .expectStatus().isBadRequest
    }

}
