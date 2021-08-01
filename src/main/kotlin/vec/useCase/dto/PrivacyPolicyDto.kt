package vec.useCase.dto

import java.time.LocalDateTime

/**
 * プライバシーポリシー
 */
data class PrivacyPolicyDto(

    /**
     * Type
     */
    val type: String = checkNotNull(PrivacyPolicyDto::class.simpleName),

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
