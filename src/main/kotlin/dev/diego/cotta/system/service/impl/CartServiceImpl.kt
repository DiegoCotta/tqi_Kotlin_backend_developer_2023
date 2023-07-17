package dev.diego.cotta.system.service.impl

import dev.diego.cotta.system.entity.Cart
import dev.diego.cotta.system.entity.CartProduct
import dev.diego.cotta.system.entity.CartProductId
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

    override fun saveAllProducts(products: List<CartProduct>){
        products.forEach {
            cartProductRepository.saveCartProducts(it.cartProductId.cartId, it.cartProductId.productId, it.quantity)
        }
    }

    override fun saveAllProductsObjects(products: List<CartProduct>) {
        cartProductRepository.saveAll(products)
    }

    override fun addProduct(product: CartProduct): Cart {
        cartProductRepository.saveCartProducts(
            product.cartProductId.cartId,
            product.cartProductId.productId,
            product.quantity)
        return cartRepository.findById(product.cartProductId.cartId)
            .orElseThrow { BusinessException("Carrinho não encontrado!") }
    }


    override fun findCartById(id: UUID): Cart =
        cartRepository.findById(id).orElseThrow { BusinessException("Carrinho não encontrado!") }

    override fun findCartProductById(cartId: UUID, productId: Long): CartProduct =
        cartProductRepository.findById(CartProductId(cartId = cartId, productId = productId)).orElseThrow {
            BusinessException("Produto não encontrado no carrinho!")
        }


    override fun findTodaySales(): List<Cart> =
        cartRepository.findAllBySale_Date(LocalDate.now())

    override fun deleteCartProduct(cartProduct: CartProduct) {
        cartProductRepository.delete(cartProduct)
    }
}
