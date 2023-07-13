package dev.diego.cotta.system.service

import dev.diego.cotta.system.entity.Product

interface ProductService {

    fun save(product: Product) : Product

    fun findAllByCategoryId(categoryId: Long): List<Product>

    fun findAll() : List<Product>

}