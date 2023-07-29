package dev.diego.cotta.system.service

import dev.diego.cotta.system.entity.Product
import dev.diego.cotta.system.exception.BusinessException
import dev.diego.cotta.system.repository.ProductRepository
import dev.diego.cotta.system.service.impl.ProductServiceImpl
import dev.diego.cotta.system.stubs.EntitiesStubs.buildProduct
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

@ExtendWith(MockKExtension::class)
class ProductServiceTest {

    @MockK
    lateinit var productRepository: ProductRepository

    @InjectMockKs
    lateinit var productService: ProductServiceImpl

    @Test
    fun `ProductService save should create Product`() {
        //given
        val fakeProduct: Product = buildProduct()
        every { productRepository.save(any()) } returns fakeProduct
        //when
        val savedCoupon = productService.save(fakeProduct)
        //then

        Assertions.assertThat(savedCoupon).isSameAs(fakeProduct)
        verify { productRepository.save(any()) }
    }

    @Test
    fun `ProductService findAll should return list of Product`() {
        //given
        val fakeListProduct: List<Product> = listOf(buildProduct(), buildProduct(name = "Sabão"))
        every { productRepository.findAll() } returns fakeListProduct
        //when
        val savedListProduct = productService.findAll()
        //then

        Assertions.assertThat(savedListProduct).isSameAs(fakeListProduct)
        verify { productRepository.findAll() }
    }

    @Test
    fun `ProductService findByName should return list of Product`() {
        //given
        val fakeListProduct: List<Product> = listOf(buildProduct())
        every { productRepository.findAllByNameContainingIgnoreCase(any()) } returns fakeListProduct
        //when
        val savedListProduct = productService.findByName("teste")
        //then

        Assertions.assertThat(savedListProduct).isSameAs(fakeListProduct)
        verify { productRepository.findAllByNameContainingIgnoreCase("teste") }
    }

    @Test
    fun `ProductService findByCategoryName should return list of Product`() {
        //given
        val fakeListProduct: List<Product> = listOf(buildProduct())
        every { productRepository.findByCategory_NameContainsIgnoreCase(any()) } returns fakeListProduct
        //when
        val savedListProduct = productService.findByCategoryName("teste")
        //then

        Assertions.assertThat(savedListProduct).isSameAs(fakeListProduct)
        verify { productRepository.findByCategory_NameContainsIgnoreCase("teste") }
    }

    @Test
    fun `ProductService findById should return Product`() {
        //given
        val fakeProduct: Product = buildProduct()
        every { productRepository.findById(any()) } returns Optional.of(fakeProduct)
        //when
        val savedListProduct = productService.findById(1L)
        //then

        Assertions.assertThat(savedListProduct).isSameAs(fakeProduct)
        verify { productRepository.findById(1L) }
    }

    @Test
    fun `ProductService findById should return error`() {
        //given
        every { productRepository.findById(any()) } returns Optional.empty()
        //when
        //then
        Assertions.assertThatExceptionOfType(BusinessException::class.java)
            .isThrownBy { productService.findById(1L) }
            .withMessage("Produto não encontrado!")
        verify { productRepository.findById(1L) }
    }
}
