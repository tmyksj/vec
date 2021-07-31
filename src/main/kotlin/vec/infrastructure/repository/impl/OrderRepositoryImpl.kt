package vec.infrastructure.repository.impl

import org.reactivestreams.Publisher
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import vec.domain.entity.Order
import vec.domain.repository.OrderRepository
import vec.infrastructure.repository.impl.OrderRepositoryImplProxy.Entity.Companion.proxy

@Component
@Transactional
class OrderRepositoryImpl(
    private val orderRepositoryImplProxy: OrderRepositoryImplProxy,
) : OrderRepository {

    override fun count(): Mono<Long> {
        return orderRepositoryImplProxy.count()
    }

    override fun delete(entity: Order): Mono<Void> {
        return orderRepositoryImplProxy.delete(entity.proxy())
    }

    override fun deleteAll(): Mono<Void> {
        return orderRepositoryImplProxy.deleteAll()
    }

    override fun deleteAll(entities: MutableIterable<Order>): Mono<Void> {
        return orderRepositoryImplProxy.deleteAll(entities.map { it.proxy() })
    }

    override fun deleteAll(entityStream: Publisher<out Order>): Mono<Void> {
        return orderRepositoryImplProxy.deleteAll(Flux.from(entityStream).map { it.proxy() })
    }

    override fun deleteAllById(ids: MutableIterable<String>): Mono<Void> {
        return orderRepositoryImplProxy.deleteAllById(ids)
    }

    override fun deleteById(id: Publisher<String>): Mono<Void> {
        return orderRepositoryImplProxy.deleteById(id)
    }

    override fun deleteById(id: String): Mono<Void> {
        return orderRepositoryImplProxy.deleteById(id)
    }

    override fun existsById(id: Publisher<String>): Mono<Boolean> {
        return orderRepositoryImplProxy.existsById(id)
    }

    override fun existsById(id: String): Mono<Boolean> {
        return orderRepositoryImplProxy.existsById(id)
    }

    override fun findAll(): Flux<Order> {
        return orderRepositoryImplProxy.findAll().map { it.proxy() }
    }

    override fun findAllById(ids: MutableIterable<String>): Flux<Order> {
        return orderRepositoryImplProxy.findAllById(ids).map { it.proxy() }
    }

    override fun findAllById(idStream: Publisher<String>): Flux<Order> {
        return orderRepositoryImplProxy.findAllById(idStream).map { it.proxy() }
    }

    override fun findAllByUserId(userId: String): Flux<Order> {
        return orderRepositoryImplProxy.findAllByUserId(userId).map { it.proxy() }
    }

    override fun findById(id: String): Mono<Order> {
        return orderRepositoryImplProxy.findById(id).map { it.proxy() }
    }

    override fun findById(id: Publisher<String>): Mono<Order> {
        return orderRepositoryImplProxy.findById(id).map { it.proxy() }
    }

    override fun <S : Order> save(entity: S): Mono<S> {
        @Suppress("UNCHECKED_CAST")
        return orderRepositoryImplProxy.save(entity.proxy()).map { it.proxy() as S }
    }

    override fun <S : Order> saveAll(entities: MutableIterable<S>): Flux<S> {
        @Suppress("UNCHECKED_CAST")
        return orderRepositoryImplProxy.saveAll(entities.map { it.proxy() }).map { it.proxy() as S }
    }

    override fun <S : Order> saveAll(entityStream: Publisher<S>): Flux<S> {
        @Suppress("UNCHECKED_CAST")
        return orderRepositoryImplProxy.saveAll(Flux.from(entityStream).map { it.proxy() }).map { it.proxy() as S }
    }

}
