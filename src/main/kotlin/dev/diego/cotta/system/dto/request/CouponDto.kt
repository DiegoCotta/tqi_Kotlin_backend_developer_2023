package dev.diego.cotta.system.dto.request

import dev.diego.cotta.system.entity.Coupon
import dev.diego.cotta.system.enum.CouponType
import jakarta.validation.constraints.Future
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal
import java.time.LocalDate

data class CouponDto(
    @field:NotNull(message = "O code é obrigatório") val code: String,
    @field:NotNull(message = "O discountType é obrigatório") val discountType: CouponType,
    @field:NotNull(message = "O discountValue é obrigatório") val discountValue: BigDecimal,
    @field:Future(message = "A data de expiração deve maior que a data de hoje") val expirationDate: LocalDate
) {
    fun toEntity(): Coupon {
        return Coupon(
            code = code,
            discountType = discountType,
            discountValue = discountValue,
            expirationDate = expirationDate
        )
    }
}
