package dev.diego.cotta.system.entity

import dev.diego.cotta.system.enum.PaymentType
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
    @Column(nullable = true, precision = 19, scale = 2) val totalPrice: BigDecimal? = null,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "coupon_id", referencedColumnName = "id", nullable = true)
    var coupon: Coupon? = null,
    @Enumerated @Column(nullable = true) val paymentType: PaymentType? = null,
    @Column(nullable = true, precision = 19, scale = 2) val totalPriceWithDiscount: BigDecimal? = null,
    @Column(nullable = true) val date: LocalDate? = null,
)
