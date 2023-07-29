package dev.diego.cotta.system.dto.request


import org.junit.jupiter.api.Test
import java.math.BigDecimal

class ProductUpdateDtoTest {

    @Test
    fun `checkFields is true`() {
        //given
        val productUpdateDto = ProductUpdateDto(
            2L, BigDecimal.valueOf(21), 3L
        )
        //when
        //then
        assert(productUpdateDto.checkFields())
    }

    @Test
    fun `checkFields is false if price and category is null`() {
        //given
        val productUpdateDto = ProductUpdateDto(
            2L, null, null
        )
        //when
        //then
        assert(productUpdateDto.checkFields().not())
    }

    @Test
    fun `checkFields is true if category is null`() {
        //given
        val productUpdateDto = ProductUpdateDto(
            2L, BigDecimal.TEN, null
        )
        //when
        //then
        assert(productUpdateDto.checkFields())
    }

    @Test
    fun `checkFields is true if price is null`() {
        //given
        val productUpdateDto = ProductUpdateDto(
            2L, null, 1L
        )
        //when
        //then
        assert(productUpdateDto.checkFields())
    }
}
