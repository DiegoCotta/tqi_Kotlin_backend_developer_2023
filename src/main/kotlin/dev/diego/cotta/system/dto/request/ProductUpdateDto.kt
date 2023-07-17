package dev.diego.cotta.system.dto.request


import jakarta.validation.constraints.AssertTrue
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

data class ProductUpdateDto(
    @field:NotNull val id: Long,
    val price: BigDecimal?,
    val quantity: BigDecimal?,
    val categoryId: Long?
) {
    @AssertTrue(message = "Preço, quantidade ou categoria devem conter um valor valido")
    fun checkFields(): Boolean {
        return price != null || quantity != null || categoryId != null
    }
}
