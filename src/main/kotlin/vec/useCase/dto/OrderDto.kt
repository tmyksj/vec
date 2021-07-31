package vec.useCase.dto

import java.time.LocalDateTime

/**
 * 注文
 */
data class OrderDto(

    /**
     * Type
     */
    val type: String = checkNotNull(OrderDto::class.simpleName),

    /**
     * ID
     */
    val id: String,

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

    )
