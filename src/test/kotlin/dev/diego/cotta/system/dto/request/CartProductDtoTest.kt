package dev.diego.cotta.system.dto.request

import dev.diego.cotta.system.entity.CartProduct
import dev.diego.cotta.system.entity.CartProductId
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.util.*


class CartProductDtoTest {

    @Test
    fun toEntity() {
        //given
        val cartId = UUID.randomUUID()
        val cartProductDto = CartProductDto(
            productId = Random().nextLong(),
            quantity = BigDecimal.valueOf(23)
        )
        val expectedEntity = CartProduct(
            cartProductId = CartProductId(cartId = cartId, productId = cartProductDto.productId),
            quantity = cartProductDto.quantity
        )
        //when
        val response = cartProductDto.toEntity(cartId)
        //then
        Assertions.assertThat(response).usingRecursiveComparison()
            .ignoringFields("fieldToIgnore")
            .isEqualTo(expectedEntity)
    }
}
