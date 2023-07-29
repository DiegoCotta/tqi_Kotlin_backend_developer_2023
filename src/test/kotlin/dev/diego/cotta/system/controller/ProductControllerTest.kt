package dev.diego.cotta.system.controller

import com.fasterxml.jackson.databind.ObjectMapper
import dev.diego.cotta.system.dto.request.ProductDto
import dev.diego.cotta.system.dto.request.ProductUpdateDto
import dev.diego.cotta.system.entity.Category
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
import java.math.BigDecimal

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration
class ProductControllerTest {

    @Autowired
    private lateinit var categoryRepository: CategoryRepository

    @Autowired
    private lateinit var productRepository: ProductRepository

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    lateinit var category: Category

    @BeforeEach
    fun setup() {
        productRepository.deleteAll()
        categoryRepository.deleteAll()
        category = categoryRepository.save(buildCategoryDto().toEntity())
    }

    @AfterEach
    fun tearDown() {
        productRepository.deleteAll()
        categoryRepository.deleteAll()
    }

    companion object {
        const val URL: String = "/product"
    }

    @Test
    fun `should create product and return status 201`() {
        //given
        val productDto: ProductDto = buildProductDto(categoryId = category.id!!)
        val valueAsString: String = objectMapper.writeValueAsString(productDto)
        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(valueAsString)
        ).andExpect(MockMvcResultMatchers.status().isCreated)
    }


    @Test
    fun `should not save product and return status 400 when field is not valid on request`() {
        //given
        val productDto: ProductDto = buildProductDto(categoryId = category.id!!, name = "")
        val valueAsString: String = objectMapper.writeValueAsString(productDto)
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
    fun `should not save product and return status 400 when category not exists`() {
        //given
        val productDto: ProductDto = buildProductDto(categoryId = 100L)
        val valueAsString: String = objectMapper.writeValueAsString(productDto)
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
                    .value("class dev.diego.cotta.system.exception.BusinessException")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `should update product return product and status 200`() {
        //given
        val product = productRepository.save(buildProductDto().toEntity(category))
        val productUpdateDto = ProductUpdateDto(product.id!!, price = BigDecimal.valueOf(15.15), categoryId = null)
        //when
        //then
        val valueAsString: String = objectMapper.writeValueAsString(productUpdateDto)
        mockMvc.perform(
            MockMvcRequestBuilders.put(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(valueAsString)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(15.15))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(product.name))
    }

    @Test
    fun `should not update product return status 400 when price and categoryId is null`() {
        //given
        val product = productRepository.save(buildProductDto().toEntity(category))
        val productUpdateDto = ProductUpdateDto(product.id!!, price = null, categoryId = null)
        //when
        //then
        val valueAsString: String = objectMapper.writeValueAsString(productUpdateDto)
        mockMvc.perform(
            MockMvcRequestBuilders.put(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(valueAsString)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
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
    fun `should list products by category and status 200`() {
        //given
        productRepository.save(buildProductDto().toEntity(category))
        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.get("$URL/find/category/${category.name}")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$").isArray)
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].price").exists())
    }

    @Test
    fun `should list products by name and status 200`() {
        //given
        val product = productRepository.save(buildProductDto().toEntity(category))
        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.get("$URL/find/name/${product.name}")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$").isArray)
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].price").exists())
    }

    @Test
    fun `should list products by id and status 200`() {
        //given
        val product = productRepository.save(buildProductDto().toEntity(category))
        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.get("$URL/find/id/${product.id}")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty)
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.price").exists())
    }

    @Test
    fun `should list all products and status 200`() {
        //given
        productRepository.save(buildProductDto().toEntity(category))
        productRepository.save(buildProductDto(name = "product 2").toEntity(category))
        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.get(URL)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$").isArray)
            .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
    }

}
