package vec.factory

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestComponent
import vec.domain.entity.Product
import vec.domain.repository.ProductRepository
import java.math.BigDecimal

@TestComponent
class ProductFactory {

    @Autowired
    private lateinit var productRepository: ProductRepository

    fun create(
        name: String = "name",
        description: String = "description",
        amount: Long = 1000,
        taxRate: BigDecimal = BigDecimal("0.10"),
        tax: Long = BigDecimal(amount).multiply(taxRate).toLong(),
        total: Long = amount + tax,
        stock: Long = 10,
    ): Product {
        return checkNotNull(
            productRepository.save(
                Product(
                    name = name,
                    description = description,
                    amount = amount,
                    taxRate = taxRate,
                    tax = tax,
                    total = total,
                    stock = stock,
                )
            ).block()
        )
    }

}
