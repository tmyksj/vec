package org.springframework.test.web.reactive.server

fun <B : WebTestClient.MockServerSpec<B>> WebTestClient.MockServerSpec<B>.applyKt(configurer: MockServerConfigurer): B {
    return apply<B>(configurer)
}
