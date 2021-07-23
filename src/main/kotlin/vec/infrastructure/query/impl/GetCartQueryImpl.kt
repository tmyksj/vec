package vec.infrastructure.query.impl

import io.r2dbc.spi.Row
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import vec.domain.entity.User
import vec.useCase.query.GetCartQuery

@Component
@Transactional
class GetCartQueryImpl(
    private val databaseClient: DatabaseClient,
) : GetCartQuery {

    override fun perform(
        principal: User,
    ): Flux<GetCartQuery.Dto> {
        return Flux.defer {
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
                           cart_product.added_date as cp_added_date
                    from cart
                             left join cart_product on cart.id = cart_product.cart_id
                             left join product on cart_product.product_id = product.id
                    where cart.user_id = :id
                """.trimIndent()
            ).bind("id", principal.id)
                .map { row, _ ->
                    GetCartQuery.Dto(
                        id = row.pick("cp_id"),
                        productId = row.pick("cp_product_id"),
                        name = row.pick("p_name"),
                        description = row.pick("p_description"),
                        amount = row.pick("p_amount"),
                        taxRate = row.pick("p_tax_rate"),
                        tax = row.pick("p_tax"),
                        total = row.pick("p_total"),
                        stock = row.pick("p_stock"),
                        addedDate = row.pick("cp_added_date"),
                    )
                }.all()
        }
    }

    private inline fun <reified T> Row.pick(name: String): T {
        return checkNotNull(get(name, T::class.java))
    }

}
