package vec.useCase.dto

import java.math.BigDecimal
import java.time.LocalDateTime

/**
 * カートにある商品
 */
data class CartProductDto(

    /**
     * Type
     */
    val type: String = checkNotNull(CartProductDto::class.simpleName),

    /**
     * ID
     */
    val id: String,

    /**
     * 商品 ID
     */
    val productId: String,

    /**
     * 商品名
     */
    val productName: String,

    /**
     * 商品の説明
     */
    val productDescription: String,

    /**
     * 金額（ JPY ）
     */
    val productAmount: Long,

    /**
     * 税率
     */
    val productTaxRate: BigDecimal,

    /**
     * 税額（ JPY ）
     */
    val productTax: Long,

    /**
     * 総額（ JPY ）
     */
    val productTotal: Long,

    /**
     * 在庫数
     */
    val productStock: Long,

    /**
     * 数量
     */
    val quantity: Long,

    /**
     * 追加日時
     */
    val addedDate: LocalDateTime,

    )
