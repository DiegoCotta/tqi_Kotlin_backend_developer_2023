package dev.diego.cotta.system.dto.request

import dev.diego.cotta.system.entity.Category
import dev.diego.cotta.system.entity.Product
import dev.diego.cotta.system.enum.MeasuringUnitType
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import java.math.BigDecimal


data class ProductDto(
    @field:NotBlank(message = "O nome do produto é obrigatório") val name: String,
    @field:NotNull(message = "A Unidade de mediada é obrigatório") val measuringUnit: MeasuringUnitType,
    @field:Positive(message = "O preço deve ser numero positivo") val price: BigDecimal,
    @field:NotNull(message = "O id da categoria é obrigatório") val categoryId: Long
) {
    fun toEntity(category: Category): Product =
        Product(
            name = name,
            measuringUnit = measuringUnit,
            price = price,
            category = category
        )
}
