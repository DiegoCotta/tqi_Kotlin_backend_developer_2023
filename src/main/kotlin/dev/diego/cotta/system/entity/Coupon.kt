package dev.diego.cotta.system.entity

import dev.diego.system.entity.CouponType
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDate

@Entity
@Table(name = "coupon")
data class Coupon(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false)
        val id: Long? = null,
        @Enumerated @Column(nullable = false) val discountType: CouponType = CouponType.FIXED,
        @Column(nullable = false) val discountValue: BigDecimal = BigDecimal.ZERO,
        @Column(nullable = false) val expirationDate: LocalDate = LocalDate.now()
)