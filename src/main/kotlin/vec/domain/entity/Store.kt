package vec.domain.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.Version
import java.time.LocalDateTime
import java.util.*

/**
 * 店舗
 */
data class Store(

    /**
     * ID
     */
    @field:Id
    val id: String = UUID.randomUUID().toString(),

    /**
     * 店舗名
     */
    val name: String,

    /**
     * 所在地
     */
    val address: String,

    /**
     * 登録日
     */
    val registeredDate: LocalDateTime,

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
