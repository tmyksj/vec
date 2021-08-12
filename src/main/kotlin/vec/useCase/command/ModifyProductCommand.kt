package vec.useCase.command

import reactor.core.publisher.Mono
import vec.domain.entity.User
import vec.useCase.dto.ProductDto
import java.math.BigDecimal

/**
 * 商品を編集します。
 */
interface ModifyProductCommand {

    /**
     * @param principal principal
     * @param id 商品 ID
     * @param name 商品名
     * @param description 商品の説明
     * @param amount 金額（ JPY ）
     * @param taxRate 税率
     * @param stock 在庫の増減数
     * @return 商品のリスト
     */
    fun perform(
        principal: User,
        id: String,
        name: String,
        description: String,
        amount: Long,
        taxRate: BigDecimal,
        stock: Long,
    ): Mono<ProductDto>

    class ProductIsNotFoundException : RuntimeException()

    class StockMustBeEnoughException : RuntimeException()

}
