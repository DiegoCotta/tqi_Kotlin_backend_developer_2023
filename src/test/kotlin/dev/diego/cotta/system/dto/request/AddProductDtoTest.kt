package dev.diego.cotta.system.dto.request

import dev.diego.cotta.system.entity.CartProduct
import dev.diego.cotta.system.entity.CartProductId
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.util.*


class AddProductDtoTest {

    @Test
    fun toEntity() {
        //given
        val addProductDto = AddProductDto(
            productId = Random().nextLong(),
            cartId = UUID.randomUUID(),
            quantity = BigDecimal.ONE)
        val expectedEntity = CartProduct(
            cartProductId = CartProductId(
                cartId = addProductDto.cartId,
                productId = addProductDto.productId
            ),
            quantity = addProductDto.quantity
        )
        //when
        val entity = addProductDto.toEntity()
        //then
        Assertions.assertThat(entity).usingRecursiveComparison()
            .ignoringFields("fieldToIgnore")
            .isEqualTo(expectedEntity)
    }
}
