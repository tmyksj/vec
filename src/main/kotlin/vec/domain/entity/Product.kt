package vec.domain.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.Version
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

/**
 * 商品
 */
data class Product(

    /**
     * ID
     */
    @field:Id
    val id: String = UUID.randomUUID().toString(),

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
    val tax: Long = BigDecimal(amount).multiply(taxRate).toLong(),

    /**
     * 総額（ JPY ）
     */
    val total: Long = amount + tax,

    /**
     * 在庫数
     */
    val stock: Long,

    /**
     * 作成日時
     */
    @field:CreatedDate
    val createdDate: LocalDateTime? = null,

    /**
     * 更新日時
     */
    @field:LastModifiedDate
    val lastModifiedDate: LocalDateTime? = null,

    /**
     * バージョン
     */
    @field:Version
    val version: Long? = null,

    ) {

    /**
     * 金額を更新します。
     * @param amount 金額（ JPY ）
     * @return product
     */
    fun modifyAmount(amount: Long): Product {
        return copy(
            amount = amount,
            tax = BigDecimal(amount).multiply(taxRate).toLong(),
            total = amount + tax,
        )
    }

    /**
     * 税率を更新します。
     * @param taxRate 税率
     * @return product
     */
    fun modifyTaxRate(taxRate: BigDecimal): Product {
        return copy(
            taxRate = taxRate,
            tax = BigDecimal(amount).multiply(taxRate).toLong(),
            total = amount + tax,
        )
    }

    /**
     * 在庫数を減らします。
     * @param stock 在庫数
     * @return product
     */
    fun reduceStock(stock: Long): Product {
        if (this.stock - stock < 0) {
            throw StockMustBeEnoughException()
        }

        return copy(
            stock = this.stock - stock,
        )
    }

    /**
     * 十分な在庫がない場合に投げられます。
     */
    class StockMustBeEnoughException : RuntimeException()

}
