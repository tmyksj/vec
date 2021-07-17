package vec.presentation.form.account

import javax.validation.constraints.AssertTrue
import javax.validation.constraints.NotNull

data class DeleteForm(

    /**
     * 確認
     */
    @field:AssertTrue
    @field:NotNull
    val confirmation: Boolean? = null,

    )
