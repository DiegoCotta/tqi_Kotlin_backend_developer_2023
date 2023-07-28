package dev.diego.cotta.system.service

import dev.diego.cotta.system.entity.Cart
import dev.diego.cotta.system.entity.CartProduct
import dev.diego.cotta.system.entity.CartProductId
import dev.diego.cotta.system.entity.Sale
import dev.diego.cotta.system.exception.BusinessException
import dev.diego.cotta.system.repository.CartProductRepository
import dev.diego.cotta.system.repository.CartRepository
import dev.diego.cotta.system.service.impl.CartServiceImpl
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

@ActiveProfiles("test")
@ExtendWith(MockKExtension::class)
class CartServiceTest {

    @MockK
    lateinit var cartRepository: CartRepository

    @MockK
    lateinit var cartProductRepository: CartProductRepository

    @InjectMockKs
    lateinit var cartService: CartServiceImpl

    @Test
    fun `cartService save should create Cart`() {
        //given
        val fakeCart: Cart = buildCart()
        every { cartRepository.save(any()) } returns fakeCart
        //when
        val savedCart = cartService.save(fakeCart)
        //then

        Assertions.assertThat(savedCart).isSameAs(fakeCart)
        verify { cartRepository.save(any()) }
    }

    @Test
    fun `cartService saveAllProducts should create CartProduct's`() {
        //given
        val fakeListCartProducts: List<CartProduct> = listOf(buildCartProduct(), buildCartProduct())
        every { cartProductRepository.saveCartProducts(any(), any(), any(), any()) } just runs
        //when
        cartService.saveAllProducts(fakeListCartProducts)
        //then

        verify(exactly = fakeListCartProducts.size)
        { cartProductRepository.saveCartProducts(any(), any(), any(), any()) }
    }

    @Test
    fun `cartService updateCartProducts should update CartProduct's`() {
        //given
        val fakeListCartProducts: List<CartProduct> = listOf(buildCartProduct(), buildCartProduct(), buildCartProduct())
        every { cartProductRepository.updateCartProductPrice(any(), any(), any()) } just runs
        //when
        cartService.updateCartProducts(fakeListCartProducts)
        //then

        verify(exactly = fakeListCartProducts.size)
        { cartProductRepository.updateCartProductPrice(any(), any(), any()) }
    }

    @Test
    fun `cartService saveAllProductsObjects should update CartProduct's`() {
        //given
        val fakeListCartProducts: List<CartProduct> = listOf(buildCartProduct(), buildCartProduct())
        every { cartProductRepository.saveAll(any<List<CartProduct>>()) } returns fakeListCartProducts
        //when
        cartService.saveAllProductsObjects(fakeListCartProducts)
        //then

        verify { cartProductRepository.saveAll(any<List<CartProduct>>()) }
    }

    @Test
    fun `cartService addProduct should add product on cart with success`() {
        //given
        val fakeCart: Cart = buildCart()
        val fakeCartProduct: CartProduct = buildCartProduct()
        every { cartProductRepository.saveCartProducts(any(), any(), any(), any()) } just runs
        every { cartRepository.findById(any()) } returns Optional.of(fakeCart)

        //when
        cartService.addProduct(fakeCartProduct)
        //then

        verify { cartProductRepository.saveCartProducts(any(), any(), any(), any()) }

    }

    @Test
    fun `cartService addProduct return error`() {
        //given
        val fakeCartProduct: CartProduct = buildCartProduct()
        every { cartProductRepository.saveCartProducts(any(), any(), any(), any()) } just runs
        every { cartRepository.findById(any()) } returns Optional.empty()

        //when
        //then
        Assertions.assertThatExceptionOfType(BusinessException::class.java)
            .isThrownBy { cartService.addProduct(fakeCartProduct) }
            .withMessage("Carrinho não encontrado!")
        verify { cartProductRepository.saveCartProducts(any(), any(), any(), any()) }

    }

    @Test
    fun `cartService findCartById return Cart`() {
        //given
        val id = UUID.randomUUID()
        val fakeCart: Cart = buildCart()
        every { cartRepository.findById(any()) } returns Optional.of(fakeCart)

        //when
        val responseCart = cartService.findCartById(id)
        //then
        Assertions.assertThat(responseCart).isSameAs(fakeCart)
        verify { cartRepository.findById(id) }
    }

    @Test
    fun `cartService findCartById return error`() {
        //given
        val id = UUID.randomUUID()
        every { cartRepository.findById(any()) } returns Optional.empty()

        //when
        //then
        Assertions.assertThatExceptionOfType(BusinessException::class.java)
            .isThrownBy { cartService.findCartById(id) }
            .withMessage("Carrinho não encontrado!")
        verify { cartRepository.findById(id) }
    }

    @Test
    fun `cartService findCartProductById return CartProduct`() {
        //given
        val cartId = UUID.randomUUID()
        val fakeCartProduct: CartProduct = buildCartProduct()
        every { cartProductRepository.findById(any()) } returns Optional.of(fakeCartProduct)

        //when
        val responseCartProduct = cartService.findCartProductById(cartId, 1L)
        //then
        Assertions.assertThat(responseCartProduct).isSameAs(fakeCartProduct)
        verify { cartProductRepository.findById(CartProductId(cartId, 1L)) }
    }

    @Test
    fun `cartService findCartProductById return error`() {
        //given
        val cartId = UUID.randomUUID()
        every { cartProductRepository.findById(any()) } returns Optional.empty()

        //when
        Assertions.assertThatExceptionOfType(BusinessException::class.java)
            .isThrownBy { cartService.findCartProductById(cartId, 2L) }
            .withMessage("Produto não encontrado no carrinho!")
        //then
        verify { cartProductRepository.findById(CartProductId(cartId, 2L)) }
    }

    @Test
    fun `cartService findTodaySales return list of Cart's`() {
        //given
        val fakeListCart: List<Cart> = listOf(buildCart(), buildCart())
        every { cartRepository.findAllBySale_Date(any()) } returns fakeListCart

        //when
        val responseCarts = cartService.findTodaySales()
        //then
        Assertions.assertThat(responseCarts).isSameAs(fakeListCart)
        verify { cartRepository.findAllBySale_Date(any()) }
    }

    @Test
    fun `cartService deleteCartProduct should delete CartProduct`() {
        //given
        val fakeCartProduct: CartProduct = buildCartProduct()
        every { cartProductRepository.delete(any()) } just runs
        //when
        cartService.deleteCartProduct(fakeCartProduct)
        //then
        verify { cartProductRepository.delete(fakeCartProduct) }
    }

    @Test
    fun `cartService hasSaleCompleted should delete CartProduct`() {
        //given
        val fakeCartProduct: CartProduct = buildCartProduct()
        every { cartProductRepository.delete(any()) } just runs
        //when
        cartService.deleteCartProduct(fakeCartProduct)
        //then
        verify { cartProductRepository.delete(fakeCartProduct) }
    }

    @Test
    fun `cartService hasSaleCompleted should return cart`() {
        //given
        val cartId = UUID.randomUUID()
        val fakeCart: Cart = buildCart()
        every { cartRepository.findById(any()) } returns Optional.of(fakeCart)
        //when
        val responseCart = cartService.hasSaleCompleted(cartId)
        //then
        Assertions.assertThat(responseCart).isSameAs(fakeCart)
        verify { cartRepository.findById(cartId) }
    }

    @Test
    fun `cartService hasSaleCompleted should return not found`() {
        //given
        val cartId = UUID.randomUUID()
        every { cartRepository.findById(any()) } returns Optional.empty()
        //when
        //then
        Assertions.assertThatExceptionOfType(BusinessException::class.java)
            .isThrownBy { cartService.hasSaleCompleted(cartId) }
            .withMessage("Carrinho não encontrado!")
        verify { cartRepository.findById(cartId) }
    }

    @Test
    fun `cartService hasSaleCompleted should return not finish`() {
        //given
        val cartId = UUID.randomUUID()
        val fakeCart: Cart = buildCart(sale = Sale(date = LocalDate.now()))
        every { cartRepository.findById(any()) } returns Optional.of(fakeCart)
        //when
        //then
        Assertions.assertThatExceptionOfType(BusinessException::class.java)
            .isThrownBy { cartService.hasSaleCompleted(cartId) }
            .withMessage("A venda desse carrinho já foi finalizado!")
    }

    companion object {
        fun buildCart(
            id: UUID = UUID.randomUUID(),
            sale: Sale = Sale(
                coupon = null,
                paymentType = null,
                totalPriceWithDiscount = null,
                date = null,
            )
        ) = Cart(
            id = id,
            sale = sale
        )

        fun buildCartProduct(
            cartProductId: CartProductId = CartProductId(cartId = UUID.randomUUID(), productId = 1L),
            quantity: BigDecimal = BigDecimal.valueOf(3.0),
            price: BigDecimal = BigDecimal.valueOf(13.0)
        ) = CartProduct(
            cartProductId = cartProductId,
            quantity = quantity,
            price = price,
        )
    }
}
