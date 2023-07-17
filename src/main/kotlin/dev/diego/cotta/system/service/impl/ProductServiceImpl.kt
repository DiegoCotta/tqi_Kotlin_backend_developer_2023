package dev.diego.cotta.system.service.impl

import dev.diego.cotta.system.entity.Product
import dev.diego.cotta.system.exception.BusinessException
import dev.diego.cotta.system.repository.ProductRepository
import dev.diego.cotta.system.service.ProductService
import org.springframework.stereotype.Service

@Service
class ProductServiceImpl(private val repository: ProductRepository) : ProductService {
    override fun save(product: Product): Product =
        repository.save(product)

    override fun findAll(): List<Product> =
        repository.findAll()

    override fun findById(id: Long): Product =
        repository.findById(id).orElseThrow { BusinessException("Produto não encontrado!") }

    override fun findByName(name: String): List<Product> =
        repository.findAllByNameContainingIgnoreCase(name)

    override fun findByCategoryName(name: String): List<Product> =
        repository.findByCategory_NameContainsIgnoreCase(name)
}
