package dev.diego.cotta.system.dto.response

import dev.diego.cotta.system.entity.Cart
import dev.diego.cotta.system.entity.Coupon
import dev.diego.cotta.system.entity.PaymentType
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

data class CartPrivateView(
    val cartId: UUID,
    val saleId: UUID,
    val totalPrice: BigDecimal,
    val coupon: Coupon?,
    val paymentType: PaymentType,
    val totalPriceWithDiscount: BigDecimal?,
    val date: LocalDate,
    val totalProducts: Int
) {
    constructor(cart: Cart) : this(
        cartId = cart.id!!,
        saleId = cart.sale!!.saleId!!,
        totalPrice = cart.sale!!.totalPrice,
        totalPriceWithDiscount = cart.sale!!.totalPriceWithDiscount,
        coupon = cart.sale!!.coupon,
        paymentType = cart.sale!!.paymentType,
        date = cart.sale!!.date,
        totalProducts = cart.products.size

    )
}
