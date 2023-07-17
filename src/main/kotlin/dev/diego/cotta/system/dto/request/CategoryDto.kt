package dev.diego.cotta.system.dto.request

import dev.diego.cotta.system.entity.Category
import jakarta.validation.constraints.NotNull

data class CategoryDto(
    @field:NotNull(message = "O name é obrigatório") val name: String
) {
    fun toEntity(): Category =
        Category(name = name)
}
