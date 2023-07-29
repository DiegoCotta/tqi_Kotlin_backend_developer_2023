package dev.diego.cotta.system.dto.request

import dev.diego.cotta.system.entity.Category
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class CategoryDtoTest {

    @Test
    fun toEntity() {
        //given
        val categoryDto = CategoryDto("category name")
        val expectedEntity = Category(
           name="category name"
        )
        //when
        val response = categoryDto.toEntity()
        //then
        Assertions.assertThat(response).usingRecursiveComparison()
            .ignoringFields("fieldToIgnore")
            .isEqualTo(expectedEntity)
    }
}
