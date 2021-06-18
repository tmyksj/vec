package vec.presentation.form.signUp

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class IndexForm(

    /**
     * メールアドレス
     */
    @field:Email
    @field:NotBlank
    @field:Size(max = 255, min = 1)
    val email: String? = null,

    /**
     * パスワード
     */
    @field:NotBlank
    @field:Size(max = 255, min = 8)
    val password: String? = null,

    /**
     * パスワード（確認）
     */
    @field:NotBlank
    @field:Size(max = 255, min = 8)
    val passwordConfirmation: String? = null,

    )
