package vec.presentation.form.signUp

import vec.presentation.validation.constraints.PasswordConfirmation
import javax.validation.constraints.Email
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@PasswordConfirmation(value = "password")
data class IndexForm(

    /**
     * メールアドレス
     */
    @field:Email
    @field:NotNull
    @field:Size(max = 255, min = 1)
    val email: String? = null,

    /**
     * パスワード
     */
    @field:NotNull
    @field:Size(max = 255, min = 8)
    val password: String? = null,

    /**
     * パスワード（確認）
     */
    @field:NotNull
    @field:Size(max = 255, min = 8)
    val passwordConfirmation: String? = null,

    )
