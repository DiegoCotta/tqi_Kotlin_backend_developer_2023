package dev.diego.cotta.system.service.impl

import dev.diego.cotta.system.entity.Cart
import dev.diego.cotta.system.entity.CartProduct
import dev.diego.cotta.system.exception.BusinessException
import dev.diego.cotta.system.repository.CartProductRepository
import dev.diego.cotta.system.repository.CartRepository
import dev.diego.cotta.system.service.CartService
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.UUID

@Service
class CartServiceImpl(
    private val cartRepository: CartRepository,
    private val cartProductRepository: CartProductRepository
) : CartService {
    override fun save(cart: Cart): Cart =
        cartRepository.save(cart)

    override fun saveAllProducts(products: List<CartProduct>): Cart {
        val result = cartProductRepository.saveAll(products)
        return result.first().cart!!
    }

    override fun addProduct(product: CartProduct): Cart =
        cartProductRepository.save(product).cart!!


    override fun findCartById(id: UUID): Cart =
        cartRepository.findById(id).orElseThrow { BusinessException("Carrinho não encontrado!") }

    override fun findTodaySales(): List<Cart> =
        cartRepository.findAllBySale_Date(LocalDate.now())
}
