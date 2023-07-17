package dev.diego.cotta.system.service

import dev.diego.cotta.system.entity.Cart
import dev.diego.cotta.system.entity.CartProduct
import jakarta.transaction.Transactional
import java.util.UUID

interface CartService {

    fun save(cart: Cart): Cart

    fun saveAllProducts(products: List<CartProduct>)

    @Transactional
    fun saveAllProductsObjects(products: List<CartProduct>)

    fun addProduct(product: CartProduct): Cart

    fun findCartById(id: UUID): Cart

    fun findCartProductById(cartId: UUID, productId: Long): CartProduct

    fun findTodaySales(): List<Cart>

    fun deleteCartProduct(cartProduct: CartProduct)

}
