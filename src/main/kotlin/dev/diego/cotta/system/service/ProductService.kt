package dev.diego.cotta.system.service

import dev.diego.cotta.system.entity.Product

interface ProductService {

    fun save(product: Product): Product

    fun findAll(): List<Product>

    fun findById(id: Long): Product

    fun findByName(name: String): List<Product>


}
