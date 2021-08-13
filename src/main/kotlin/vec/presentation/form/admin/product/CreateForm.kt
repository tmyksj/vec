package vec.presentation.form.admin.product

import java.math.BigDecimal
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class CreateForm(

    /**
     * 商品名
     */
    @field:NotBlank
    val name: String? = null,

    /**
     * 商品の説明
     */
    @field:NotBlank
    val description: String? = null,

    /**
     * 金額（ JPY ）
     */
    @field:Min(value = 0L)
    @field:NotNull
    val amount: Long? = null,

    /**
     * 税率
     */
    @field:Min(value = 0L)
    @field:NotNull
    val taxRate: BigDecimal? = null,

    /**
     * 在庫数
     */
    @field:Min(value = 0L)
    @field:NotNull
    val stock: Long? = null,

    )
