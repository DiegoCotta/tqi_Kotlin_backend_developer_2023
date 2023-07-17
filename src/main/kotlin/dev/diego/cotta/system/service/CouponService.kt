package dev.diego.cotta.system.service

import dev.diego.cotta.system.entity.Coupon

interface CouponService {
    fun save(coupon: Coupon): Coupon

    fun listCoupons() : List<Coupon>

    fun findByCode(code: String): Coupon
}
