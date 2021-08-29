package vec.presentation.form.storeRegistration

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class IndexForm(

    /**
     * 店舗名
     */
    @field:NotBlank
    val name: String? = null,

    /**
     * 所在地
     */
    @field:NotBlank
    val address: String? = null,

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
