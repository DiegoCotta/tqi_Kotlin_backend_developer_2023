package dev.diego.cotta.system.repository

import dev.diego.cotta.system.entity.Coupon
import org.springframework.data.jpa.repository.JpaRepository

interface CouponRepository : JpaRepository<Coupon, Long> {
    fun findByCode(code: String): Coupon?
}
