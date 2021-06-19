package vec.integration.signIn

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.applyKt
import vec.factory.UserFactory

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
            .get().uri("/sign-in")
            .exchange()
            .expectStatus().isOk
    }

    @Test
    fun get_responds_303_when_user_signed_in() {
        WebTestClient.bindToApplicationContext(applicationContext)
            .applyKt(SecurityMockServerConfigurers.springSecurity())
            .build()
            .mutateWith(SecurityMockServerConfigurers.mockUser(userFactory.create()))
            .get().uri("/sign-in")
            .exchange()
            .expectStatus().isSeeOther
    }

}
