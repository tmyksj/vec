package vec.useCase.command

import reactor.core.publisher.Mono
import vec.domain.entity.User
import vec.useCase.dto.CartProductDto

/**
 * カートに商品を追加します。
 */
interface AddToCartCommand {

    /**
     * @param principal principal
     * @param productId 商品 ID
     * @param quantity 数量
     * @return 追加した商品
     * @throws ProductIsNotFoundException
     */
    fun perform(
        principal: User,
        productId: String,
        quantity: Long,
    ): Mono<CartProductDto>

    class ProductIsNotFoundException : RuntimeException()

}
