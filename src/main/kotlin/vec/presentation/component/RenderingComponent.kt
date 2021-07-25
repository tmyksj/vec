package vec.presentation.component

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.result.view.Rendering
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class RenderingComponent {

    fun redirect(url: String): RedirectBuilder {
        return RedirectBuilder(url)
    }

    fun restoreRedirectAttributes(serverWebExchange: ServerWebExchange): Mono<Map<String, Any?>> {
        return Mono.defer {
            serverWebExchange.session
        }.map { webSession ->
            val attrs: Any? = webSession.attributes.remove(REDIRECT_ATTRIBUTES_ATTR_NAME)

            if (attrs is Map<*, *>) {
                @Suppress("UNCHECKED_CAST")
                attrs.filterKeys { it is String } as Map<String, Any?>
            } else {
                mapOf()
            }
        }
    }

    fun view(name: String): ViewBuilder {
        return ViewBuilder(name)
    }

    companion object {

        const val REDIRECT_ATTRIBUTES_ATTR_NAME: String = "REDIRECT_ATTRIBUTES"

    }

    interface Builder {

        fun build(serverWebExchange: ServerWebExchange): Mono<Rendering>

    }

    class RedirectBuilder(
        private val url: String,
        private val map: MutableMap<String, Any?> = mutableMapOf(),
        private var status: HttpStatus = HttpStatus.SEE_OTHER,
    ) : Builder {

        override fun build(serverWebExchange: ServerWebExchange): Mono<Rendering> {
            return Mono.defer {
                serverWebExchange.session
            }.doOnNext {
                it.attributes[REDIRECT_ATTRIBUTES_ATTR_NAME] = map
            }.map {
                Rendering.redirectTo(url)
                    .status(status)
                    .build()
            }
        }

        fun redirectAttribute(name: String, value: Any?): RedirectBuilder {
            map[name] = value
            return this
        }

        fun status(status: HttpStatus): RedirectBuilder {
            this.status = status
            return this
        }

    }

    class ViewBuilder(
        private val name: String,
        private val map: MutableMap<String, Any?> = mutableMapOf(),
        private var status: HttpStatus = HttpStatus.OK,
    ) : Builder {

        override fun build(serverWebExchange: ServerWebExchange): Mono<Rendering> {
            return Mono.fromCallable {
                Rendering.view(name)
                    .model(map)
                    .status(status)
                    .build()
            }
        }

        fun modelAttribute(name: String, value: Any?): ViewBuilder {
            map[name] = value
            return this
        }

        fun status(status: HttpStatus): ViewBuilder {
            this.status = status
            return this
        }

    }

}
