package dev.diego.cotta.system.dto.response

import dev.diego.cotta.system.entity.CartProduct
import dev.diego.cotta.system.entity.Product
import dev.diego.cotta.system.enum.MeasuringUnitType
import java.math.BigDecimal

data class CartProductsPrivateView(
    val name: String,
    val measuringUnitType: MeasuringUnitType,
    val price: BigDecimal?,
    val quantity: BigDecimal
) {
    constructor(product: Product, cartProduct: CartProduct) : this(
        name = product.name,
        measuringUnitType = product.measuringUnit,
        price = cartProduct.price,
        quantity = cartProduct.quantity
    )
}
