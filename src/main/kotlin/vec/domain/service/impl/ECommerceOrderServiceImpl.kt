package vec.domain.service.impl

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.onErrorMap
import reactor.kotlin.core.publisher.switchIfEmptyDeferred
import vec.domain.entity.Order
import vec.domain.entity.OrderProduct
import vec.domain.entity.Product
import vec.domain.entity.User
import vec.domain.repository.*
import vec.domain.service.ECommerceOrderService
import vec.domain.service.SecurityService
import java.time.LocalDateTime

@Component
@Transactional
class ECommerceOrderServiceImpl(
    private val cartProductRepository: CartProductRepository,
    private val cartRepository: CartRepository,
    private val orderProductRepository: OrderProductRepository,
    private val orderRepository: OrderRepository,
    private val productRepository: ProductRepository,
    private val securityService: SecurityService,
) : ECommerceOrderService {

    override fun order(user: User): Mono<Order> {
        return Mono.defer {
            securityService.requireRoleConsumer(user)
        }.flatMap {
            orderRepository.save(
                Order(
                    userId = user.id,
                    amount = 0L,
                    tax = 0L,
                    total = 0L,
                    orderedDate = LocalDateTime.now(),
                )
            )
        }.flatMap { order ->
            cartRepository.findByUserId(user.id)
                .flatMapMany {
                    cartProductRepository.findAllByCartId(it.id)
                }.switchIfEmptyDeferred {
                    throw ECommerceOrderService.CartProductMustExistException()
                }.flatMap { cartProduct ->
                    productRepository.findById(cartProduct.productId)
                        .map {
                            it.reduceStock(cartProduct.quantity)
                        }.onErrorMap(Product.StockMustBeEnoughException::class) {
                            ECommerceOrderService.ProductMustExistException()
                        }.flatMap {
                            Mono.zip(
                                cartProductRepository.delete(cartProduct).thenReturn(cartProduct),
                                orderProductRepository.save(
                                    OrderProduct(
                                        orderId = order.id,
                                        productId = it.id,
                                        productName = it.name,
                                        productDescription = it.description,
                                        productAmount = it.amount,
                                        productTaxRate = it.taxRate,
                                        productTax = it.tax,
                                        productTotal = it.total,
                                        quantity = cartProduct.quantity,
                                        amount = cartProduct.quantity * it.amount,
                                        tax = cartProduct.quantity * it.tax,
                                        total = cartProduct.quantity * it.total,
                                    )
                                ),
                                productRepository.save(it),
                            )
                        }
                }.reduce(Triple(0L, 0L, 0L)) { t, u ->
                    t.copy(t.first + u.t2.amount, t.second + u.t2.tax, t.third + u.t2.total)
                }.flatMap {
                    orderRepository.save(
                        order.copy(
                            amount = it.first,
                            tax = it.second,
                            total = it.third,
                        )
                    )
                }
        }
    }

}
