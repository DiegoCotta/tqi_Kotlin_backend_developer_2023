package dev.diego.cotta.system.dto.request


import dev.diego.cotta.system.validation.RequestAnnotation
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

@RequestAnnotation(message = "Preço, quantidade ou categoria devem conter um valor valido" )
data class ProductUpdateDto(
    @field:NotNull val id: Long,
    val price: BigDecimal?,
    val quantity: BigDecimal?,
    val categoryId: Long?
) {
    fun checkFields(): Boolean {
        return price != null || quantity != null || categoryId != null
    }
}
