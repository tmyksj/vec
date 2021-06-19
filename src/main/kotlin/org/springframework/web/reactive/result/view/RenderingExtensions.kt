package org.springframework.web.reactive.result.view

fun <B : Rendering.Builder<B>> Rendering.Builder<B>.modelAttribute(name: String, value: Any?): B {
    return model(mapOf(name to value))
}
