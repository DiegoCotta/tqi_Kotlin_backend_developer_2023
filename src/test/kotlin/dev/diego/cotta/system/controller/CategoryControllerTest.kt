package dev.diego.cotta.system.controller

import com.fasterxml.jackson.databind.ObjectMapper
import dev.diego.cotta.system.dto.request.CategoryDto
import dev.diego.cotta.system.repository.CategoryRepository
import dev.diego.cotta.system.repository.ProductRepository
import dev.diego.cotta.system.stubs.DtoStubs.buildCategoryDto
import dev.diego.cotta.system.stubs.DtoStubs.buildProductDto
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration
class CategoryControllerTest {
    @Autowired
    private lateinit var categoryRepository: CategoryRepository

    @Autowired
    private lateinit var productRepository: ProductRepository

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper


    @BeforeEach
    fun setup() {
        productRepository.deleteAll()
        categoryRepository.deleteAll()
    }

    @AfterEach
    fun tearDown() {
        productRepository.deleteAll()
        categoryRepository.deleteAll()
    }

    companion object {
        const val URL: String = "/category"
    }

    @Test
    fun `should create category and return status 201`() {
        //given
        val categoryDto: CategoryDto = buildCategoryDto()
        val valueAsString: String = objectMapper.writeValueAsString(categoryDto)
        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(valueAsString)
        ).andExpect(MockMvcResultMatchers.status().isCreated)
    }

    @Test
    fun `should not save category and return status 409`() {
        //given
        categoryRepository.save(buildCategoryDto().toEntity())
        val categoryDto: CategoryDto = buildCategoryDto()
        val valueAsString: String = objectMapper.writeValueAsString(categoryDto)
        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(valueAsString)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun `should not save category and return status 400 when field is not valid on request`() {
        //given
        val categoryDto: CategoryDto = buildCategoryDto("")
        val valueAsString: String = objectMapper.writeValueAsString(categoryDto)
        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(valueAsString)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Bad Request! Consult the documentation"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.exception")
                    .value("class org.springframework.web.bind.MethodArgumentNotValidException")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `should return list of category's and status 200`() {
        //given
        categoryRepository.save(buildCategoryDto().toEntity())
        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.get(URL)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$").isArray)
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").exists())
    }

    @Test
    fun `should delete category and status 204`() {
        //given
        val customer = categoryRepository.save(buildCategoryDto().toEntity())
        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.delete("$URL/${customer.id}")
        )
            .andExpect(MockMvcResultMatchers.status().isNoContent)
    }

    @Test
    fun `should not delete category with products and return 400 status`() {
        //given
        val category = categoryRepository.save(buildCategoryDto().toEntity())
        productRepository.save(buildProductDto().toEntity(category))
        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.delete("$URL/${category.id}")
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Bad Request! Consult the documentation"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.exception")
                    .value("class dev.diego.cotta.system.exception.BusinessException")
            )
            .andDo(MockMvcResultHandlers.print())
    }
}
