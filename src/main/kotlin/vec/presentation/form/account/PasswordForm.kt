package vec.presentation.form.account

import vec.presentation.validation.constraints.PasswordConfirmation
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@PasswordConfirmation(value = "newPassword")
data class PasswordForm(

    /**
     * 現在のパスワード
     */
    @field:NotNull
    @field:Size(max = 255, min = 8)
    val currentPassword: String? = null,

    /**
     * 新しいパスワード
     */
    @field:NotNull
    @field:Size(max = 255, min = 8)
    val newPassword: String? = null,

    /**
     * 新しいパスワード（確認）
     */
    @field:NotNull
    @field:Size(max = 255, min = 8)
    val newPasswordConfirmation: String? = null,

    )
