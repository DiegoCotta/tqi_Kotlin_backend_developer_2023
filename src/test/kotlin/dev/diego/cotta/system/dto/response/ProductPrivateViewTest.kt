package dev.diego.cotta.system.dto.response

import dev.diego.cotta.system.entity.Category
import dev.diego.cotta.system.entity.Product
import dev.diego.cotta.system.enum.MeasuringUnitType
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class ProductPrivateViewTest {

    @Test
    fun secondaryConstructor() {
        //given
        val productPrivateView = ProductPrivateView(
            Product(
                id = 1L,
                name = "product name",
                measuringUnit = MeasuringUnitType.UN,
                price = BigDecimal.ZERO,
                category = Category()
            )
        )
        val expected = ProductPrivateView(
            id = 1L,
            name = "product name",
            measuringUnit = MeasuringUnitType.UN,
            price = BigDecimal.ZERO,
            category = Category()
        )
        //when
        //then
        Assertions.assertThat(productPrivateView).usingRecursiveComparison()
            .ignoringFields("fieldToIgnore")
            .isEqualTo(expected)

    }
}
