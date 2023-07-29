package dev.diego.cotta.system.dto.request

import dev.diego.cotta.system.entity.Category
import dev.diego.cotta.system.entity.Product
import dev.diego.cotta.system.enum.MeasuringUnitType
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.math.BigDecimal


class ProductDtoTest {

    @Test
    fun toEntity() {
        //given
        val category = Category(1L, "category name")
        val productDto = ProductDto(
            name = "product",
            measuringUnit = MeasuringUnitType.CT,
            price = BigDecimal(43.2),
            categoryId = 3L
        )
        val expectedEntity = Product(
            name = "product",
            measuringUnit = MeasuringUnitType.CT,
            price = BigDecimal(43.2),
            category = category
        )
        //when
        val entity = productDto.toEntity(category)
        //then
        Assertions.assertThat(entity).usingRecursiveComparison()
            .ignoringFields("fieldToIgnore")
            .isEqualTo(expectedEntity)
    }
}
