package vec.presentation.form.account

import javax.validation.constraints.Email
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class EmailForm(

    /**
     * メールアドレス
     */
    @field:Email
    @field:NotNull
    @field:Size(max = 255, min = 1)
    val email: String? = null,

    )
