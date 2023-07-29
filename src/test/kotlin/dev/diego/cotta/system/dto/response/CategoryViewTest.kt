package dev.diego.cotta.system.dto.response

import dev.diego.cotta.system.entity.Category
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class CategoryViewTest {

    @Test
    fun secondaryConstructor() {
        //given
        val categoryView = CategoryView(Category(id = 1L, name="Category Name"))
        val expected = CategoryView(
            id = 1L, name="Category Name"
        )
        //when
        //then
        Assertions.assertThat(categoryView).usingRecursiveComparison()
            .ignoringFields("fieldToIgnore")
            .isEqualTo(expected)
    }
}
