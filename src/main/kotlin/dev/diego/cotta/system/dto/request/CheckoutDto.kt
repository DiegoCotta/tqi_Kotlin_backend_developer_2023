package dev.diego.cotta.system.dto.request

import dev.diego.cotta.system.entity.Cart
import dev.diego.cotta.system.entity.Coupon
import dev.diego.cotta.system.entity.Sale
import dev.diego.cotta.system.entity.CouponType
import dev.diego.cotta.system.entity.PaymentType
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

import java.util.UUID

data class CheckoutDto(
    @field:NotNull(message = "O id do carrinho é obrigatório") val cartId: UUID,
    val couponCode: String?,
    @field:NotNull(message = "O método de pagamento é obrigatório") val paymentType: PaymentType
) {
    fun toEntity(cart: Cart, coupon: Coupon?): Sale {
        val totalPrice = cart.products.sumOf {
            (it.product?.price ?: BigDecimal.ZERO).multiply(BigDecimal.valueOf(it.quantity.toLong()))
        }
        return Sale(
            totalPrice = totalPrice,
            paymentType = paymentType,
            coupon = coupon,
            totalPriceWithDiscount = coupon?.let {
                when (it.discountType) {
                    CouponType.FIXED -> {
                        totalPrice.subtract(coupon.discountValue)
                    }

                    CouponType.PERCENTAGE -> {
                        totalPrice.subtract(coupon.discountValue)
                    }
                }
            }
        )
    }
}