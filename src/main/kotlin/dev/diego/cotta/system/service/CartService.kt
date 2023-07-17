package dev.diego.cotta.system.service

import dev.diego.cotta.system.entity.Cart
import dev.diego.cotta.system.entity.CartProduct
import jakarta.transaction.Transactional
import java.util.UUID

interface CartService {

    fun save(cart: Cart): Cart

    @Transactional
    fun saveAllProducts(products: List<CartProduct>): Cart

    fun addProduct(product: CartProduct): Cart

    fun findCartById(id: UUID): Cart

    fun findTodaySales(): List<Cart>

}
