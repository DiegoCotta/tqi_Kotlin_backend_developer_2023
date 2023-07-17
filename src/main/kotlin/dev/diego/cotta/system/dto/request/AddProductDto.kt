package dev.diego.cotta.system.dto.request

import dev.diego.cotta.system.entity.CartProduct
import dev.diego.cotta.system.entity.CartProductId
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import java.math.BigDecimal
import java.util.UUID

data class AddProductDto(
    @field:NotNull(message = "O id do produto é obrigatório") val productId: Long,
    @field:NotNull(message = "O carrinho do produto é obrigatório") val cartId: UUID,
    @field:Positive(message = "A quantidade deve ser um valor positivo") val quantity: BigDecimal
) {
    fun toEntity(): CartProduct =
        CartProduct(
            cartProductId = CartProductId(cartId = cartId, productId = productId),
            quantity = quantity
        )
}
