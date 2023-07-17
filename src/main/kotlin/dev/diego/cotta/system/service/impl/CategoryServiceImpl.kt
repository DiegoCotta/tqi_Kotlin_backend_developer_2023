package dev.diego.cotta.system.service.impl

import dev.diego.cotta.system.entity.Category
import dev.diego.cotta.system.exception.BusinessException
import dev.diego.cotta.system.repository.CategoryRepository
import dev.diego.cotta.system.service.CategoryService
import org.springframework.stereotype.Service

@Service
class CategoryServiceImpl(private val repository: CategoryRepository) : CategoryService {
    override fun save(category: Category): Category =
        repository.save(category)

    override fun findAll(): List<Category> =
        repository.findAll()

    override fun findById(id: Long): Category =
        repository.findById(id).orElseThrow { BusinessException("Categoria não encontrada!") }
}
