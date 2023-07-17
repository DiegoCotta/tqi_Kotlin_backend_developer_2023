package dev.diego.cotta.system.entity

import dev.diego.cotta.system.enum.CouponType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

import java.math.BigDecimal
import java.time.LocalDate

@Entity
@Table(name = "coupon")
class Coupon(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(nullable = false) val code: String,
    @Enumerated @Column(nullable = false) val discountType: CouponType = CouponType.FIXED,
    @Column(nullable = false, precision = 19, scale = 2) val discountValue: BigDecimal = BigDecimal.ZERO,
    @Column(nullable = false) val expirationDate: LocalDate = LocalDate.now()
)
