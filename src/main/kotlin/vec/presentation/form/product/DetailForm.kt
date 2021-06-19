package vec.presentation.form.product

import javax.validation.constraints.NotBlank

data class DetailForm(

    /**
     * 商品 ID
     */
    @field:NotBlank
    val id: String? = null,

    )
