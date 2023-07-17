package dev.diego.cotta.system.service

import dev.diego.cotta.system.entity.Category

interface CategoryService {

    fun save(category: Category): Category

    fun findAll(): List<Category>

    fun findById(id: Long): Category

    fun deleteById(id: Long)
}
