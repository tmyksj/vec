package vec.domain.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.Version
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import java.util.*

/**
 * 注文
 */
@Table(value = "`order`")
data class Order(

    /**
     * ID
     */
    @field:Id
    val id: String = UUID.randomUUID().toString(),

    /**
     * ユーザ ID
     */
    val userId: String,

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
     * 注文日時
     */
    val orderedDate: LocalDateTime,

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
