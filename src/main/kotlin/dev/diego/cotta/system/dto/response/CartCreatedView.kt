package dev.diego.cotta.system.dto.response

import dev.diego.cotta.system.entity.Cart
import java.util.UUID

data class CartCreatedView(
    val cartCode: UUID,
    val products: List<ProductPublicView>
) {
    constructor(cart: Cart) : this(
        cartCode = cart.id!!,
        products = cart.products.map { ProductPublicView(it.product!!) }
    )
}
