package dev.diego.cotta.system.entity

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

@Embeddable
class Sale(
    @GeneratedValue(strategy = GenerationType.UUID)
    val saleId: UUID? = null,
    @Column(nullable = false) val totalPrice: BigDecimal = BigDecimal.ZERO,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "coupon_id", referencedColumnName = "id")
    var coupon: Coupon? = null,
    @Enumerated @Column(nullable = false) val paymentType: PaymentType = PaymentType.DEBIT,
    @Column(nullable = true) val totalPriceWithDiscount: BigDecimal? = null,
    @Column(nullable = false) val date: LocalDate = LocalDate.now(),
)
