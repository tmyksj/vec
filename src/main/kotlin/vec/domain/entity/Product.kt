package vec.domain.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime
import java.util.*

/**
 * 商品
 */
data class Product(

    /**
     * ID
     */
    @field:Id
    val id: UUID = UUID.randomUUID(),

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
     * 在庫数
     */
    val stock: Long,

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

    )
