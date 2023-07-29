package dev.diego.cotta.system.dto.response

import dev.diego.cotta.system.entity.Cart
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.util.*

class CartCreatedViewTest {

    @Test
    fun secondaryConstructor() {
        //given
        val uuid = UUID.randomUUID()
        val cartCreatedView = CartCreatedView(Cart(id = uuid))
        val expected = CartCreatedView(
            cartCode = uuid,
            products = listOf()
        )
        //when
        //then
        Assertions.assertThat(cartCreatedView).usingRecursiveComparison()
            .ignoringFields("fieldToIgnore")
            .isEqualTo(expected)
    }

}
