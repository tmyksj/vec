package vec.useCase.dto

/**
 * 利用統計
 */
data class UsageStatisticsDto(

    /**
     * Type
     */
    val type: String = checkNotNull(UsageStatisticsDto::class.simpleName),

    /**
     * 消費者ユーザ数
     */
    val consumerCount: Long,

    /**
     * 商品数
     */
    val productCount: Long,

    /**
     * 注文数
     */
    val orderCount: Long,

    )
