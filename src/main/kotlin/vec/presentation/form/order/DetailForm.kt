package vec.presentation.form.order

import javax.validation.constraints.NotBlank

data class DetailForm(

    /**
     * 注文 ID
     */
    @field:NotBlank
    val id: String? = null,

    )
