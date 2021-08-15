package vec.useCase.dto

import java.time.LocalDateTime

/**
 * 店舗利用同意文書
 */
data class StoreAgreementDto(

    /**
     * Type
     */
    val type: String = checkNotNull(StoreAgreementDto::class.simpleName),

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
