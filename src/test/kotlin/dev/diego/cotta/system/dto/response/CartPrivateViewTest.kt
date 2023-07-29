package dev.diego.cotta.system.dto.response

import dev.diego.cotta.system.entity.Cart
import dev.diego.cotta.system.entity.Sale
import dev.diego.cotta.system.enum.PaymentType
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

class CartPrivateViewTest {

    @Test
    fun secondaryConstructor() {
        //given
        val uuid = UUID.randomUUID()
        val cartPrivateView = CartPrivateView(
            Cart(id = uuid,
                sale = Sale(date = LocalDate.now(), paymentType = PaymentType.CREDIT, totalPrice = BigDecimal.TEN))
        )
        val expected = CartPrivateView(
            cartId = uuid,
            totalPrice = BigDecimal.TEN,
            coupon = null,
            paymentType = PaymentType.CREDIT,
            totalPriceWithDiscount = null,
            date = LocalDate.now(),
            totalProducts = 0,
            products = listOf()
        )
        //when
        //then
        Assertions.assertThat(cartPrivateView).usingRecursiveComparison()
            .ignoringFields("fieldToIgnore")
            .isEqualTo(expected)
    }
}
