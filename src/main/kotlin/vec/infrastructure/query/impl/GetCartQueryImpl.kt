package vec.infrastructure.query.impl

import io.r2dbc.spi.Row
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import vec.domain.entity.User
import vec.domain.repository.CartRepository
import vec.useCase.dto.CartDto
import vec.useCase.dto.CartProductDto
import vec.useCase.query.GetCartQuery

@Component
@Transactional
class GetCartQueryImpl(
    private val databaseClient: DatabaseClient,
    private val cartRepository: CartRepository,
) : GetCartQuery {

    override fun perform(
        principal: User,
    ): Flux<out Any> {
        return Flux.defer {
            Flux.merge(
                cartRepository.findByUserId(principal.id)
                    .map {
                        CartDto(
                            id = it.id,
                            userId = it.userId,
                        )
                    },
                databaseClient.sql(
                    """
                        select cart_product.id         as cp_id,
                               cart_product.product_id as cp_product_id,
                               product.name            as p_name,
                               product.description     as p_description,
                               product.amount          as p_amount,
                               product.tax_rate        as p_tax_rate,
                               product.tax             as p_tax,
                               product.total           as p_total,
                               product.stock           as p_stock,
                               cart_product.quantity   as cp_quantity,
                               cart_product.added_date as cp_added_date
                        from cart
                                 inner join cart_product on cart.id = cart_product.cart_id
                                 inner join product on cart_product.product_id = product.id
                        where cart.user_id = :id
                        order by cart_product.added_date desc
                    """.trimIndent()
                ).bind("id", principal.id)
                    .map { row, _ ->
                        CartProductDto(
                            id = row.pick("cp_id"),
                            productId = row.pick("cp_product_id"),
                            productName = row.pick("p_name"),
                            productDescription = row.pick("p_description"),
                            productAmount = row.pick("p_amount"),
                            productTaxRate = row.pick("p_tax_rate"),
                            productTax = row.pick("p_tax"),
                            productTotal = row.pick("p_total"),
                            productStock = row.pick("p_stock"),
                            quantity = row.pick("cp_quantity"),
                            addedDate = row.pick("cp_added_date"),
                        )
                    }.all(),
            )
        }
    }

    private inline fun <reified T> Row.pick(name: String): T {
        return checkNotNull(get(name, T::class.java))
    }

}
