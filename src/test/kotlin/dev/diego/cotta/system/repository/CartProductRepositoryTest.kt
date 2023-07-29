package dev.diego.cotta.system.repository

import dev.diego.cotta.system.entity.Cart
import dev.diego.cotta.system.entity.CartProductId
import dev.diego.cotta.system.entity.Product
import dev.diego.cotta.system.entity.Sale
import dev.diego.cotta.system.stubs.EntitiesStubs.buildCart
import dev.diego.cotta.system.stubs.EntitiesStubs.buildCartProduct
import dev.diego.cotta.system.stubs.EntitiesStubs.buildCategory
import dev.diego.cotta.system.stubs.EntitiesStubs.buildProduct
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal
import java.time.LocalDate

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CartProductRepositoryTest {

    @Autowired
    lateinit var cartProductRepository: CartProductRepository

    @Autowired
    lateinit var testEntityManager: TestEntityManager

    private lateinit var product: Product
    private lateinit var cart: Cart

    @BeforeEach
    fun setup() {
        val category = testEntityManager.persist(buildCategory(id = null))
        product = testEntityManager.persist(buildProduct(id = null, category = category))
        cart = testEntityManager.persist(
            buildCart(
                id = null,
                sale = Sale(date = LocalDate.now(),
                    totalPrice = BigDecimal.TEN
                )
            )
        )
    }

    @Test
    fun `should save cartProduct`() {
        //given
        val expectedCartProduct = buildCartProduct(
            cartProductId = CartProductId(cartId = cart.id!!, productId = product.id!!),
            quantity = BigDecimal.ONE,
            price = BigDecimal.TEN,
            product = product,
            cart = cart
        )

        //when
        cartProductRepository.saveCartProducts(
            cartID = cart.id!!, productId = product.id!!, quantity = BigDecimal.ONE, price = BigDecimal.TEN)
        //then
        val response = cartProductRepository.findById(CartProductId(cartId = cart.id!!, productId = product.id!!))
        Assertions.assertThat(response).isNotNull
        Assertions.assertThat(expectedCartProduct)
            .usingRecursiveComparison()
            .ignoringFields("fieldToIgnore")
            .isEqualTo(expectedCartProduct)
    }

    @Test
    fun `should update cartProduct`() {
        //given
        val expectedCartProduct = testEntityManager.persist(
            buildCartProduct(
                cartProductId = CartProductId(cartId = cart.id!!, productId = product.id!!),
                quantity = BigDecimal.ONE,
                price = BigDecimal.TEN,
                product = product,
                cart = cart
            )
        )
        expectedCartProduct.price = BigDecimal.ONE
        //when
        cartProductRepository.updateCartProductPrice(
            cartID = cart.id!!, productId = product.id!!, price = BigDecimal.ONE)
        //then
        val response = cartProductRepository.findById(CartProductId(cartId = cart.id!!, productId = product.id!!))
        Assertions.assertThat(response).isNotNull
        Assertions.assertThat(expectedCartProduct).isEqualTo(response.get())
    }

}



