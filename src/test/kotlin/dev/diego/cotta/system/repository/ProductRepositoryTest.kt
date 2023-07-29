package dev.diego.cotta.system.repository

import dev.diego.cotta.system.entity.Product
import dev.diego.cotta.system.stubs.Stubs.buildCategory
import dev.diego.cotta.system.stubs.Stubs.buildProduct
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {

    @Autowired
    lateinit var productRepository: ProductRepository

    @Autowired
    lateinit var testEntityManager: TestEntityManager

    private lateinit var product: Product
    private lateinit var product1: Product

    @BeforeEach
    fun setup() {
        val category = testEntityManager.persist(buildCategory(id = null, name = "Limpeza"))
        val category2 = testEntityManager.persist(buildCategory(id = null, name = "Cozinha"))
        product = testEntityManager.persist(buildProduct(id = null, name = "teste 1", category = category))
        product1 = testEntityManager.persist(buildProduct(id = null, name = "teste 2", category = category2))
    }

    @Test
    fun `should find products by name`() {
        //given
        val testName = "tes"

        //when
        val response: List<Product> = productRepository.findAllByNameContainingIgnoreCase(testName)
        //then
        Assertions.assertThat(response).isNotNull
        Assertions.assertThat(response).isNotEmpty
        Assertions.assertThat(response).isEqualTo(listOf(product, product1))
    }

    @Test
    fun `should return empty list products when find by name`() {
        //given
        val testName = "error"

        //when
        val response: List<Product> = productRepository.findAllByNameContainingIgnoreCase(testName)
        //then
        Assertions.assertThat(response).isNotNull
        Assertions.assertThat(response).isEmpty()
    }

    @Test
    fun `should find products by category name`() {
        //given
        val categoryName = "Limpeza"

        //when
        val response: List<Product> = productRepository.findByCategory_NameContainsIgnoreCase(categoryName)
        //then
        Assertions.assertThat(response).isNotNull
        Assertions.assertThat(response).isNotEmpty()
        Assertions.assertThat(response).isEqualTo(listOf(product))
    }

    @Test
    fun `should return empty list products when find by category name`() {
        //given
        val categoryName = "teste"

        //when
        val response: List<Product> = productRepository.findByCategory_NameContainsIgnoreCase(categoryName)
        //then
        Assertions.assertThat(response).isNotNull
        Assertions.assertThat(response).isEmpty()
    }

}



