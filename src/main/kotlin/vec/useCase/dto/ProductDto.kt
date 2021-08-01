package vec.useCase.dto

import java.math.BigDecimal

/**
 * 商品
 */
data class ProductDto(

    /**
     * Type
     */
    val type: String = checkNotNull(ProductDto::class.simpleName),

    /**
     * ID
     */
    val id: String,

    /**
     * 商品名
     */
    val name: String,

    /**
     * 商品の説明
     */
    val description: String,

    /**
     * 金額（ JPY ）
     */
    val amount: Long,

    /**
     * 税率
     */
    val taxRate: BigDecimal,

    /**
     * 税額（ JPY ）
     */
    val tax: Long,

    /**
     * 総額（ JPY ）
     */
    val total: Long,

    /**
     * 在庫数
     */
    val stock: Long,

    )
