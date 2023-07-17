package dev.diego.cotta.system.dto.request

import dev.diego.cotta.system.entity.Category
import dev.diego.cotta.system.entity.MeasuringUnitType
import dev.diego.cotta.system.entity.Product
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import java.math.BigDecimal


data class ProductDto(
    @field:NotNull(message = "O nome do produto é obrigatório") val name: String,
    @field:NotNull(message = "A Unidade de mediada é obrigatório") val measuringUnit: MeasuringUnitType,
    @field:Positive(message = "O preço deve ser numero positivo") val price: BigDecimal,
    @field:Positive(message = "O preço deve ser numero positivo") val quantity: BigDecimal = BigDecimal.ZERO,
    @field:NotNull(message = "O id da categoria é obrigatório") val categoryId: Long
) {
    fun toEntity(category: Category): Product =
        Product(
            name = name,
            measuringUnit = measuringUnit,
            price = price,
            quantity = quantity,
            category = category

        )
}
