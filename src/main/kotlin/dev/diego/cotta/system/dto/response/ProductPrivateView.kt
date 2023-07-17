package dev.diego.cotta.system.dto.response

import dev.diego.cotta.system.entity.Category
import dev.diego.cotta.system.entity.Product
import dev.diego.cotta.system.enum.MeasuringUnitType
import java.math.BigDecimal

data class ProductPrivateView(
    val id: Long? = null,
    val name: String = "",
    val measuringUnit: MeasuringUnitType = MeasuringUnitType.UN,
    var price: BigDecimal = BigDecimal.ZERO,
    var quantity: BigDecimal = BigDecimal.ZERO,
    var category: Category
) {
    constructor(product: Product) : this(
        name = product.name,
        id = product.id,
        measuringUnit = product.measuringUnit,
        price = product.price,
        quantity = product.quantity,
        category = product.category
    )
}
