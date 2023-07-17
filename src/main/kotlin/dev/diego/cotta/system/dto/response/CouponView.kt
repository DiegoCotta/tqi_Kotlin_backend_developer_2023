package dev.diego.cotta.system.dto.response

import dev.diego.cotta.system.entity.Coupon
import dev.diego.cotta.system.entity.CouponType
import java.text.DecimalFormat
import java.util.*

data class CouponView(val code: String, val discount: String, val expirationDate: String) {
    constructor(coupon: Coupon) : this(code = coupon.code, discount = when (coupon.discountType) {
        CouponType.FIXED ->
            String.format(Locale.ROOT, "R$ %s",
                DecimalFormat("#0.00").format(coupon.discountValue))

        CouponType.PERCENTAGE ->
            String.format(Locale.ROOT, "%s%%",
                DecimalFormat("#0.##").format(coupon.discountValue))
    }, expirationDate = coupon.expirationDate.toString())

}
