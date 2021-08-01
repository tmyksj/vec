package vec.factory

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestComponent
import vec.domain.entity.Order
import vec.domain.repository.OrderRepository
import java.time.LocalDateTime

@TestComponent
class OrderFactory {

    @Autowired
    private lateinit var orderRepository: OrderRepository

    @Autowired
    private lateinit var userFactory: UserFactory

    fun create(
        userId: String = userFactory.create().id,
        amount: Long = 1000L,
        tax: Long = 100L,
        total: Long = amount + tax,
        orderedDate: LocalDateTime = LocalDateTime.now(),
    ): Order {
        return checkNotNull(
            orderRepository.save(
                Order(
                    userId = userId,
                    amount = amount,
                    tax = tax,
                    total = total,
                    orderedDate = orderedDate,
                )
            ).block()
        )
    }

}
