package dev.diego.cotta.system.dto.request

import dev.diego.cotta.system.entity.Coupon
import dev.diego.cotta.system.enum.CouponType
import jakarta.validation.constraints.Future
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import java.math.BigDecimal
import java.time.LocalDate

data class CouponDto(
    @field:NotBlank(message = "O code é obrigatório") val code: String,
    @field:NotNull(message = "O discountType é obrigatório") val discountType: CouponType,
    @field:Positive(message = "O discountValue é obrigatório e maior que 0") val discountValue: BigDecimal,
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
