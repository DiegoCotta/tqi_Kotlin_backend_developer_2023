package dev.diego.cotta.system.repository

import dev.diego.cotta.system.entity.Cart
import dev.diego.cotta.system.entity.Sale
import dev.diego.cotta.system.stubs.EntitiesStubs.buildCart
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
class CartRepositoryTest {

    @Autowired
    lateinit var cartRepository: CartRepository

    @Autowired
    lateinit var testEntityManager: TestEntityManager

    private lateinit var cart: Cart

    @BeforeEach
    fun setup() {
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
    fun `should find cart by date`() {
        //given
        val todayDate = LocalDate.now()

        //when
        val response: List<Cart> = cartRepository.findAllBySale_Date(todayDate)
        //then
        Assertions.assertThat(response).isNotNull
        Assertions.assertThat(listOf(cart)).isEqualTo(response)
    }

    @Test
    fun `should not find cart by date`() {
        //given
        val passDate = LocalDate.MIN

        //when
        val response: List<Cart> = cartRepository.findAllBySale_Date(passDate)
        //then
        Assertions.assertThat(response).isNotNull
        Assertions.assertThat(response).isEmpty()
    }

}



