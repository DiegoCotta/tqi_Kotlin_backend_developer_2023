package dev.diego.cotta.system.service.impl

import dev.diego.cotta.system.entity.Product
import dev.diego.cotta.system.repository.ProductRepository
import dev.diego.cotta.system.service.ProductService
import org.springframework.stereotype.Service

@Service
class ProductServiceImpl(val repository: ProductRepository) : ProductService {
    override fun save(product: Product): Product =
        repository.save(product)


    override fun findAllByCategoryId(categoryId: Long): List<Product> =
        repository.findAllByCategoryId(categoryId)

    override fun findAll(): List<Product> =
        repository.findAll()
}