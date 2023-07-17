package dev.diego.cotta.system.dto.response

import dev.diego.cotta.system.entity.Product
import dev.diego.cotta.system.enum.MeasuringUnitType
import java.math.BigDecimal

data class ProductPublicView(
    val name: String,
    val measuringUnit: MeasuringUnitType,
    val price: BigDecimal,
    val quantity: BigDecimal,
    val id: Long,
    val categoryName: String
) {
    constructor(product: Product) : this(
        name = product.name,
        measuringUnit = product.measuringUnit,
        price = product.price,
        quantity = product.quantity,
        id = product.id ?: 0L,
        categoryName = product.category.name
    )
}
