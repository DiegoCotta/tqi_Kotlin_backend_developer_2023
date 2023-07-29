package dev.diego.cotta.system.service

import dev.diego.cotta.system.entity.Category
import dev.diego.cotta.system.exception.BusinessException
import dev.diego.cotta.system.repository.CategoryRepository
import dev.diego.cotta.system.service.impl.CategoryServiceImpl
import dev.diego.cotta.system.stubs.Stubs.buildCategory
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.dao.DataIntegrityViolationException
import java.util.*

@ExtendWith(MockKExtension::class)
class CategoryServiceTest {
    @MockK
    lateinit var categoryRepository: CategoryRepository

    @InjectMockKs
    lateinit var categoryService: CategoryServiceImpl

    @Test
    fun `categoryService save should create Category`() {
        //given
        val fakeCategory: Category = buildCategory()
        every { categoryRepository.save(any()) } returns fakeCategory
        //when
        val savedCategory = categoryService.save(fakeCategory)
        //then

        Assertions.assertThat(savedCategory).isSameAs(fakeCategory)
        verify { categoryRepository.save(any()) }
    }

    @Test
    fun `categoryService findAll should return list of Category`() {
        //given
        val fakeListCategory = listOf(buildCategory(), buildCategory(name = "Limpeza"))
        every { categoryRepository.findAll() } returns fakeListCategory
        //when
        val savedListCategory = categoryService.findAll()
        //then

        Assertions.assertThat(savedListCategory).isSameAs(fakeListCategory)
        verify { categoryRepository.findAll() }
    }

    @Test
    fun `categoryService findById should return Category`() {
        //given
        val fakeCategory: Category = buildCategory()
        every { categoryRepository.findById(any()) } returns Optional.of(fakeCategory)
        //when
        val savedCategory = categoryService.findById(1L)
        //then

        Assertions.assertThat(savedCategory).isSameAs(fakeCategory)
        verify { categoryRepository.findById(1L) }
    }

    @Test
    fun `categoryService findById should return error`() {
        //given
        every { categoryRepository.findById(any()) } returns Optional.empty()
        //when
        //then
        Assertions.assertThatExceptionOfType(BusinessException::class.java)
            .isThrownBy { categoryService.findById(1L) }
            .withMessage("Categoria não encontrada!")
        verify { categoryRepository.findById(1L) }
    }

    @Test
    fun `categoryService delete should remove category`() {    //given
        val fakeId: Long = Random().nextLong()
        every { categoryRepository.deleteById(any()) } just runs
        //when
        categoryService.deleteById(fakeId)
        //then
        verify { categoryRepository.deleteById(fakeId) }


    }

    @Test
    fun `categoryService delete should return BusinessException`() {    //given
        every { categoryRepository.deleteById(any()) } throws DataIntegrityViolationException("error")
        //when
        //then
        Assertions.assertThatExceptionOfType(BusinessException::class.java)
            .isThrownBy { categoryService.deleteById(1L) }
            .withMessage("Não é possível excluir uma categoria com produtos existentes!")
        verify { categoryRepository.deleteById(1L) }
    }

}
