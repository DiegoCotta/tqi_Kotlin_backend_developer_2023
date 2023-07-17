package dev.diego.cotta.system.dto.response

import dev.diego.cotta.system.entity.Category

data class CategoryView(
    val id: Long,
    val name: String
) {
    constructor(category: Category) : this(
        id = category.id ?: 0L,
        name = category.name
    )
}
