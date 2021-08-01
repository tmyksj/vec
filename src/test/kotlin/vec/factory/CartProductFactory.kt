package vec.factory

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestComponent
import vec.domain.entity.CartProduct
import vec.domain.repository.CartProductRepository
import java.time.LocalDateTime

@TestComponent
class CartProductFactory {

    @Autowired
    private lateinit var cartProductRepository: CartProductRepository

    @Autowired
    private lateinit var cartFactory: CartFactory

    @Autowired
    private lateinit var productFactory: ProductFactory

    fun create(
        cartId: String = cartFactory.create().id,
        productId: String = productFactory.create().id,
        quantity: Long = 10L,
        addedDate: LocalDateTime = LocalDateTime.now(),
    ): CartProduct {
        return checkNotNull(
            cartProductRepository.save(
                CartProduct(
                    cartId = cartId,
                    productId = productId,
                    quantity = quantity,
                    addedDate = addedDate,
                )
            ).block()
        )
    }

}
