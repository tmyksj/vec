package vec.domain.service

import reactor.core.publisher.Mono
import vec.domain.entity.Order
import vec.domain.entity.User

/**
 * e-commerce の注文に関するサービスです。
 */
interface ECommerceOrderService {

    /**
     * カートにある商品を注文します。
     */
    fun order(user: User): Mono<Order>

    class CartProductMustExistException : RuntimeException()

    class ProductMustExistException : RuntimeException()

}
