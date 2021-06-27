package vec.presentation.form.cart

import javax.validation.constraints.NotBlank

data class IndexForm(

    /**
     * 商品 ID
     */
    @field:NotBlank
    val productId: String? = null,

    )
