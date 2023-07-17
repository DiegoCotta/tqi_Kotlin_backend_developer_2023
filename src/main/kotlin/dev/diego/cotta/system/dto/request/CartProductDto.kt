package dev.diego.cotta.system.dto.request

import dev.diego.cotta.system.entity.CartProduct
import dev.diego.cotta.system.entity.CartProductId
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal
import java.util.UUID

data class CartProductDto(
    @field:NotNull(message = "O código do produto é obrigatório") val productId: Long,
    @field:NotNull(message = "A quantidade é obrigatório") val quantity: BigDecimal
) {
    fun toEntity(cartId: UUID): CartProduct = CartProduct(
        cartProductId = CartProductId(
            cartId = cartId,
            productId = productId),
        quantity = quantity
    )

}
