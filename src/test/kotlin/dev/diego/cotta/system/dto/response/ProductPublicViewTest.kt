package dev.diego.cotta.system.dto.response


import dev.diego.cotta.system.entity.Category
import dev.diego.cotta.system.entity.Product
import dev.diego.cotta.system.enum.MeasuringUnitType
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class ProductPublicViewTest {

    @Test
    fun secondaryConstructor() {
        //given
        val productPublicView = ProductPublicView(
            Product(
                id = 1L,
                name = "product name",
                measuringUnit = MeasuringUnitType.UN,
                price = BigDecimal.ZERO,
                category = Category(1L, "category")
            )
        )
        val expected = ProductPublicView(
            id = 1L,
            name = "product name",
            measuringUnit = MeasuringUnitType.UN,
            price = BigDecimal.ZERO,
            categoryName = "category"
        )
        //when
        //then
        Assertions.assertThat(productPublicView).usingRecursiveComparison()
            .ignoringFields("fieldToIgnore")
            .isEqualTo(expected)
    }
}
