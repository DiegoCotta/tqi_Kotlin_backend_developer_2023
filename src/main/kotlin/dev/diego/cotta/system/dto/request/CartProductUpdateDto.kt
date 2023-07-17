package dev.diego.cotta.system.dto.request

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import java.util.UUID

data class CartProductUpdateDto(
    @field:NotNull(message = "Código do carrinho é obrigatório!") val cartId: UUID,
    @field:NotEmpty(message = "Deve conter ao menos um produto") val products: List<CartProductDto>
)
