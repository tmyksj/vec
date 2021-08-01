package vec.useCase.dto

import java.math.BigDecimal

/**
 * 注文商品
 */
data class OrderProductDto(

    /**
     * Type
     */
    val type: String = checkNotNull(OrderProductDto::class.simpleName),

    /**
     * ID
     */
    val id: String,

    /**
     * 注文 ID
     */
    val orderId: String,

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
     * 商品の金額（ JPY ）
     */
    val productAmount: Long,

    /**
     * 商品の税率
     */
    val productTaxRate: BigDecimal,

    /**
     * 商品の税額（ JPY ）
     */
    val productTax: Long,

    /**
     * 商品の総額（ JPY ）
     */
    val productTotal: Long,

    /**
     * 数量
     */
    val quantity: Long,

    /**
     * 金額（ JPY ）
     */
    val amount: Long,

    /**
     * 税額（ JPY ）
     */
    val tax: Long,

    /**
     * 総額（ JPY ）
     */
    val total: Long,

    )
