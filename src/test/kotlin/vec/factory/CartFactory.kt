package vec.factory

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestComponent
import vec.domain.entity.Cart
import vec.domain.repository.CartRepository

@TestComponent
class CartFactory {

    @Autowired
    private lateinit var cartRepository: CartRepository

    @Autowired
    private lateinit var userFactory: UserFactory

    fun create(
        userId: String = userFactory.create().id,
    ): Cart {
        return checkNotNull(
            cartRepository.save(
                Cart(
                    userId = userId,
                )
            ).block()
        )
    }

}
