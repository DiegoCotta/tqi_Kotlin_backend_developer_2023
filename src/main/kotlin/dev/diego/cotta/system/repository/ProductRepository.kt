package dev.diego.cotta.system.repository

import dev.diego.cotta.system.entity.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Long> {

    fun findAllByNameContainingIgnoreCase(name: String): List<Product>

    @SuppressWarnings("FunctionNaming")
    fun findByCategory_NameContainsIgnoreCase(name: String): List<Product>


}
