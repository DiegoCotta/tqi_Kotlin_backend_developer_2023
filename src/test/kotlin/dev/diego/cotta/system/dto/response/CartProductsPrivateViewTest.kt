package dev.diego.cotta.system.dto.response

import dev.diego.cotta.system.entity.CartProduct
import dev.diego.cotta.system.entity.CartProductId
import dev.diego.cotta.system.entity.Category
import dev.diego.cotta.system.entity.Product
import dev.diego.cotta.system.enum.MeasuringUnitType
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.util.*

class CartProductsPrivateViewTest {
    @Test
    fun secondaryConstructor() {

        //given
        val uuid = UUID.randomUUID()
        val cartProductsPrivateView = CartProductsPrivateView(
            product = Product(
                1L, "Product Name", MeasuringUnitType.KG, BigDecimal.ONE, Category(null, "category")
            ),
            cartProduct = CartProduct(
                CartProductId(uuid,1L), price= BigDecimal.TEN, quantity = BigDecimal.ONE
            )
        )
        val expected = CartProductsPrivateView(
            name = "Product Name",
            measuringUnitType = MeasuringUnitType.KG,
            price = BigDecimal.TEN,
            quantity = BigDecimal.ONE
        )
        //when
        //then
        Assertions.assertThat(cartProductsPrivateView).usingRecursiveComparison()
            .ignoringFields("fieldToIgnore")
            .isEqualTo(expected)
    }
}
