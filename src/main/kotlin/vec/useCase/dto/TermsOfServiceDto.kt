package vec.useCase.dto

import java.time.LocalDateTime

/**
 * 利用規約
 */
data class TermsOfServiceDto(

    /**
     * Type
     */
    val type: String = checkNotNull(TermsOfServiceDto::class.simpleName),

    /**
     * ID
     */
    val id: String,

    /**
     * 本文
     */
    val body: String,

    /**
     * 適用日時
     */
    val appliedDate: LocalDateTime,

    )
