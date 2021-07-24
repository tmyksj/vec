package vec.domain.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.Version
import java.time.LocalDateTime
import java.util.*

/**
 * カートにある商品
 */
data class CartProduct(

    /**
     * ID
     */
    @field:Id
    val id: String = UUID.randomUUID().toString(),

    /**
     * カート ID
     */
    val cartId: String,

    /**
     * 商品 ID
     */
    val productId: String,

    /**
     * 数量
     */
    val quantity: Long,

    /**
     * 追加日時
     */
    val addedDate: LocalDateTime,

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

    ) {

    /**
     * 数量を増やします。
     * @param quantity 数量
     * @return cart product
     */
    fun increaseQuantity(quantity: Long): CartProduct {
        return copy(
            quantity = this.quantity + quantity,
        )
    }

}
