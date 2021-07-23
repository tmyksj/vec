package vec.useCase.command

import reactor.core.publisher.Mono
import vec.domain.entity.CartProduct
import vec.domain.entity.User

/**
 * カートに商品を追加します。
 */
interface AddToCartCommand {

    /**
     * @param principal principal
     * @param productId 商品 ID
     * @return 追加した商品
     * @throws ProductIsNotFoundException
     */
    fun perform(
        principal: User,
        productId: String,
    ): Mono<CartProduct>

    class ProductIsNotFoundException : RuntimeException()

}
