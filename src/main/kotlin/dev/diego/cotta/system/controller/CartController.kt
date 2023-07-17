package dev.diego.cotta.system.controller

import dev.diego.cotta.system.dto.request.AddProductDto
import dev.diego.cotta.system.dto.request.CartDto
import dev.diego.cotta.system.dto.request.CartProductUpdateDto
import dev.diego.cotta.system.dto.request.CheckoutDto
import dev.diego.cotta.system.dto.response.CartCreatedView
import dev.diego.cotta.system.dto.response.CartPrivateView
import dev.diego.cotta.system.dto.response.SaleView
import dev.diego.cotta.system.entity.Cart
import dev.diego.cotta.system.service.CartService
import dev.diego.cotta.system.service.CouponService
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
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
        cart.id?.let { service.saveAllProducts(cartDto.products.map { it.toEntity(cart.id) }) }
        service.findCartById(cart.id!!)
        return ResponseEntity.ok(CartCreatedView(service.findCartById(cart.id!!)))
    }

    @PostMapping("/checkout")
    fun saveCart(@RequestBody @Valid checkoutDto: CheckoutDto): ResponseEntity<SaleView> {
        val coupon = checkoutDto.couponCode?.let { serviceCoupon.findByCode(it) }
        val cart = service.findCartById(checkoutDto.cartId)
        cart.sale = checkoutDto.toEntity(cart, coupon)
        val response = service.save(cart)
        return ResponseEntity.ok(SaleView(response.id!!))
    }


    @PutMapping("/add")
    fun updateCartProducts(@RequestBody @Valid cartDto: AddProductDto): ResponseEntity<CartCreatedView> {
        val cart = service.addProduct(cartDto.toEntity())
        return ResponseEntity.ok(CartCreatedView(cart))
    }


    @PutMapping("/update-product")
    fun updateCartProducts(@RequestBody @Valid cartDto: CartProductUpdateDto): ResponseEntity<CartCreatedView> {
        val products = cartDto.products.map { product ->
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
}
