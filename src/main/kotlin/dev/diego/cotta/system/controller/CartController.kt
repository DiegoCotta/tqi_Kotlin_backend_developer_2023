package dev.diego.cotta.system.controller

import dev.diego.cotta.system.dto.request.AddProductDto
import dev.diego.cotta.system.dto.request.CartDto
import dev.diego.cotta.system.dto.request.CartProductUpdateDto
import dev.diego.cotta.system.dto.request.CheckoutDto
import dev.diego.cotta.system.dto.response.CartCreatedView
import dev.diego.cotta.system.dto.response.CartPrivateView
import dev.diego.cotta.system.dto.response.SaleView
import dev.diego.cotta.system.entity.Cart
import dev.diego.cotta.system.exception.BusinessException
import dev.diego.cotta.system.service.CartService
import dev.diego.cotta.system.service.CouponService
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal
import java.util.*

@RestController
@RequestMapping("/cart")
class CartController(
    private val service: CartService,
    private val serviceCoupon: CouponService,
) {
    @PostMapping
    @Transactional
    fun saveCart(@RequestBody @Valid cartDto: CartDto): ResponseEntity<CartCreatedView> {
        val cart = service.save(Cart())
        cart.id?.let {
            val products = cartDto.products.filter {
                it.quantity != BigDecimal.ZERO
            }.map { it.toEntity(cart.id) }
            service.saveAllProducts(products)
        }
        return ResponseEntity.ok(CartCreatedView(cart))
    }

    @PostMapping("/checkout")
    @Transactional
    fun saveCart(@RequestBody @Valid checkoutDto: CheckoutDto): ResponseEntity<SaleView> {
        val cart = service.hasSaleCompleted(checkoutDto.cartId!!)
        val coupon = checkoutDto.couponCode?.let { serviceCoupon.findByCode(it) }
        cart.products.map { it.price = it.product?.price }
        service.updateCartProducts(cart.products)
        cart.sale = checkoutDto.toEntity(cart, coupon)
        val response = service.save(cart)
        return ResponseEntity.ok(SaleView(response.id!!))
    }


    @PutMapping("/add")
    fun updateCartProducts(@RequestBody @Valid cartDto: AddProductDto): ResponseEntity<CartCreatedView> {
        service.hasSaleCompleted(cartDto.cartId)
        try {
            service.findCartProductById(cartDto.cartId, cartDto.productId)
        } catch (ignore: Exception) {
            val cart = service.addProduct(cartDto.toEntity())
            return ResponseEntity.ok(CartCreatedView(cart))
        }
        throw BusinessException("O produto já existe no carrinho!")
    }


    @PutMapping("/update-product")
    fun updateCartProducts(@RequestBody @Valid cartDto: CartProductUpdateDto): ResponseEntity<CartCreatedView> {
        service.hasSaleCompleted(cartDto.cartId)
        cartDto.products.map { product ->
            service.findCartProductById(cartDto.cartId, product.productId)
                .apply {
                    quantity = product.quantity
                }
        }
        return ResponseEntity.ok(CartCreatedView(service.findCartById(cartDto.cartId)))
    }

    @GetMapping
    fun getCart(@RequestParam("id") id: UUID): ResponseEntity<CartPrivateView> =
        ResponseEntity.ok(CartPrivateView(service.findCartById(id)))

    @GetMapping("/today-sales")
    fun getTodaySales(): ResponseEntity<List<CartPrivateView>> =
        ResponseEntity.ok(service.findTodaySales().map { CartPrivateView(it) })

    @DeleteMapping("/remove/product/{productId}/cart/{cartId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCartProduct(@PathVariable productId: Long, @PathVariable cartId: UUID) {
        service.hasSaleCompleted(cartId)
        val cartProduct = service.findCartProductById(cartId = cartId, productId = productId)
        service.deleteCartProduct(cartProduct)
    }
}
