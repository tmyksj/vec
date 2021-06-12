package vec.presentation.form

import javax.validation.constraints.NotBlank

object ProductForm {

    data class Detail(

        /**
         * 商品 ID
         */
        @field:NotBlank
        val id: String? = null,

        )

}
