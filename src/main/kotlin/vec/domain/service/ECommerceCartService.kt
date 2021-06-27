package vec.domain.service

import reactor.core.publisher.Mono
import vec.domain.entity.CartProduct
import vec.domain.entity.Product
import vec.domain.entity.User

/**
 * e-commerce のカートに関するサービスです。
 */
interface ECommerceCartService {

    /**
     * ユーザのカートに商品を追加します。
     * @param user ユーザ
     * @param product 商品
     * @return 追加した商品もしくは empty
     */
    fun add(user: User, product: Product): Mono<CartProduct>

}