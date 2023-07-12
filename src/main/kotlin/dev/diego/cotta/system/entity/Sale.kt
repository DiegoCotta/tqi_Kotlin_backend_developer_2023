package dev.diego.cotta.system.entity

import dev.diego.system.entity.PaymentType
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

@Entity
@Table(name = "sale")
data class Sale(
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        val id: UUID? = null,
        @Column(nullable = false) val totalPrice: BigDecimal = BigDecimal.ZERO,
        @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "coupon_id", referencedColumnName = "id")
        var coupon: Coupon? = null,
        @Enumerated @Column(nullable = false) val paymentType: PaymentType = PaymentType.DEBIT,
        @Column(nullable = true) val totalPriceWithDiscount: BigDecimal = BigDecimal.ZERO,
        @Column(nullable = false) val date: LocalDate = LocalDate.now(),
        @OneToOne @JoinColumn(name = "cart_id", referencedColumnName = "id") val cart: Cart?
)