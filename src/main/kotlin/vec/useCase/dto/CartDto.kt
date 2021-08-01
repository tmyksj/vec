package vec.useCase.dto

/**
 * カート
 */
data class CartDto(

    /**
     * Type
     */
    val type: String = checkNotNull(CartDto::class.simpleName),

    /**
     * ID
     */
    val id: String,

    /**
     * ユーザ ID
     */
    val userId: String,

    )
