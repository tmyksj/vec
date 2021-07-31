package vec.infrastructure.repository.impl

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.Version
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import vec.domain.entity.Order
import java.time.LocalDateTime

@Component
@Transactional
interface OrderRepositoryImplProxy : ReactiveCrudRepository<OrderRepositoryImplProxy.Entity, String> {

    fun findAllByUserId(userId: String): Flux<Entity>

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

        ) {

        companion object {

            fun Order.proxy(): Entity {
                return Entity(
                    id = id,
                    userId = userId,
                    amount = amount,
                    tax = tax,
                    total = total,
                    orderedDate = orderedDate,
                    createdDate = createdDate,
                    lastModifiedDate = lastModifiedDate,
                    version = version,
                )
            }

            fun Entity.proxy(): Order {
                return Order(
                    id = id,
                    userId = userId,
                    amount = amount,
                    tax = tax,
                    total = total,
                    orderedDate = orderedDate,
                    createdDate = createdDate,
                    lastModifiedDate = lastModifiedDate,
                    version = version,
                )
            }

        }

    }

}

