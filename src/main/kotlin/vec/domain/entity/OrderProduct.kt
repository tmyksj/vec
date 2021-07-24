package vec.domain.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.Version
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

/**
 * 注文商品
 */
data class OrderProduct(

    /**
     * ID
     */
    @field:Id
    val id: String = UUID.randomUUID().toString(),

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

    /**
     * 作成日時
     */
    @field:CreatedDate
    val createdDate: LocalDateTime? = null,

    /**
     * 更新日時
     */
    @field:LastModifiedDate
    val lastModifiedDate: LocalDateTime? = null,

    /**
     * バージョン
     */
    @field:Version
    val version: Long? = null,

    )
