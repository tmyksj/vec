package vec.presentation.form.account

import javax.validation.constraints.AssertTrue
import javax.validation.constraints.NotNull

data class DeleteForm(

    /**
     * 確認
     */
    @field:AssertTrue(message = "{vec.presentation.form.account.DeleteForm.confirmation.AssertTrue.message}")
    @field:NotNull
    val confirmation: Boolean? = null,

    )
