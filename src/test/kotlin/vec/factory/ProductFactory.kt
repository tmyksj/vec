package vec.factory

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestComponent
import vec.domain.entity.Product
import vec.domain.repository.ProductRepository

@TestComponent
class ProductFactory {

    @Autowired
    private lateinit var productRepository: ProductRepository

    fun save(
        name: String = "name",
        description: String = "description",
        amount: Long = 1000,
        stock: Long = 10,
    ): Product {
        return checkNotNull(
            productRepository.save(
                Product(
                    name = name,
                    description = description,
                    amount = amount,
                    stock = stock,
                )
            ).block()
        )
    }

}
