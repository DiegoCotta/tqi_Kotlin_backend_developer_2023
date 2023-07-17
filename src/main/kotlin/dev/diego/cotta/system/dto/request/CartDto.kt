package dev.diego.cotta.system.dto.request

import jakarta.validation.constraints.NotEmpty

data class CartDto(
    @field:NotEmpty(message = "Deve conter ao menos um produto") val products: List<CartProductDto>
)
