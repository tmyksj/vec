package vec.useCase.query

import reactor.core.publisher.Flux
import vec.domain.entity.User
import java.math.BigDecimal
import java.time.LocalDateTime

/**
 * カートを取得します。
 * 追加日時の降順です。
 */
interface GetCartQuery {

    /**
     * @param principal principal
     * @return
     */
    fun perform(
        principal: User,
    ): Flux<Dto>

    data class Dto(

        /**
         * ID
         */
        val id: String,

        /**
         * 商品 ID
         */
        val productId: String,

        /**
         * 商品名
         */
        val name: String,

        /**
         * 商品の説明
         */
        val description: String,

        /**
         * 金額（ JPY ）
         */
        val amount: Long,

        /**
         * 税率
         */
        val taxRate: BigDecimal,

        /**
         * 税額（ JPY ）
         */
        val tax: Long,

        /**
         * 総額（ JPY ）
         */
        val total: Long,

        /**
         * 在庫数
         */
        val stock: Long,

        /**
         * 数量
         */
        val quantity: Long,

        /**
         * 追加日時
         */
        val addedDate: LocalDateTime,

        )

}
