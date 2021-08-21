package vec.infrastructure.repository.impl

import org.reactivestreams.Publisher
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.Version
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import vec.domain.entity.Order
import vec.domain.repository.OrderRepository
import java.time.LocalDateTime

@Component
@Transactional
class OrderRepositoryImpl(
    private val proxy: OrderRepositoryProxy,
) : OrderRepository {

    override fun count(): Mono<Long> {
        return proxy.count()
    }

    override fun delete(entity: Order): Mono<Void> {
        return proxy.delete(convert(entity))
    }

    override fun deleteAll(): Mono<Void> {
        return proxy.deleteAll()
    }

    override fun deleteAll(entities: MutableIterable<Order>): Mono<Void> {
        return proxy.deleteAll(entities.map(::convert))
    }

    override fun deleteAll(entityStream: Publisher<out Order>): Mono<Void> {
        return proxy.deleteAll(Flux.from(entityStream).map(::convert))
    }

    override fun deleteAllById(ids: MutableIterable<String>): Mono<Void> {
        return proxy.deleteAllById(ids)
    }

    override fun deleteById(id: Publisher<String>): Mono<Void> {
        return proxy.deleteById(id)
    }

    override fun deleteById(id: String): Mono<Void> {
        return proxy.deleteById(id)
    }

    override fun existsById(id: Publisher<String>): Mono<Boolean> {
        return proxy.existsById(id)
    }

    override fun existsById(id: String): Mono<Boolean> {
        return proxy.existsById(id)
    }

    override fun findAll(): Flux<Order> {
        return proxy.findAll().map(::convert)
    }

    override fun findAllById(ids: MutableIterable<String>): Flux<Order> {
        return proxy.findAllById(ids).map(::convert)
    }

    override fun findAllById(idStream: Publisher<String>): Flux<Order> {
        return proxy.findAllById(idStream).map(::convert)
    }

    override fun findAllByUserId(userId: String): Flux<Order> {
        return proxy.findAllByUserId(userId).map(::convert)
    }

    override fun findById(id: String): Mono<Order> {
        return proxy.findById(id).map(::convert)
    }

    override fun findById(id: Publisher<String>): Mono<Order> {
        return proxy.findById(id).map(::convert)
    }

    override fun findByIdAndUserId(id: String, userId: String): Mono<Order> {
        return proxy.findByIdAndUserId(id, userId).map(::convert)
    }

    override fun <S : Order> save(entity: S): Mono<S> {
        @Suppress("UNCHECKED_CAST")
        return proxy.save(convert(entity)).map { convert(it) as S }
    }

    override fun <S : Order> saveAll(entities: MutableIterable<S>): Flux<S> {
        @Suppress("UNCHECKED_CAST")
        return proxy.saveAll(entities.map(::convert)).map { convert(it) as S }
    }

    override fun <S : Order> saveAll(entityStream: Publisher<S>): Flux<S> {
        @Suppress("UNCHECKED_CAST")
        return proxy.saveAll(Flux.from(entityStream).map(::convert)).map { convert(it) as S }
    }

    private fun convert(source: Order): OrderRepositoryProxy.Entity {
        return OrderRepositoryProxy.Entity(
            id = source.id,
            userId = source.userId,
            amount = source.amount,
            tax = source.tax,
            total = source.total,
            orderedDate = source.orderedDate,
            createdDate = source.createdDate,
            lastModifiedDate = source.lastModifiedDate,
            version = source.version,
        )
    }

    private fun convert(source: OrderRepositoryProxy.Entity): Order {
        return Order(
            id = source.id,
            userId = source.userId,
            amount = source.amount,
            tax = source.tax,
            total = source.total,
            orderedDate = source.orderedDate,
            createdDate = source.createdDate,
            lastModifiedDate = source.lastModifiedDate,
            version = source.version,
        )
    }

}

@Component
@Transactional
interface OrderRepositoryProxy : ReactiveCrudRepository<OrderRepositoryProxy.Entity, String> {

    fun findAllByUserId(userId: String): Flux<Entity>

    fun findByIdAndUserId(id: String, userId: String): Mono<Entity>

    @Table(value = "`order`")
    data class Entity(

        @field:Id
        val id: String,

        val userId: String,

        val amount: Long,

        val tax: Long,

        val total: Long,

        val orderedDate: LocalDateTime,

        @field:CreatedDate
        val createdDate: LocalDateTime?,

        @field:LastModifiedDate
        val lastModifiedDate: LocalDateTime?,

        @field:Version
        val version: Long?,

        )

}
