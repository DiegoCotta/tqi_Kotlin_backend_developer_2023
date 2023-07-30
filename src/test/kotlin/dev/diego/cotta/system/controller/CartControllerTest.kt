package dev.diego.cotta.system.controller

import com.fasterxml.jackson.databind.ObjectMapper
import dev.diego.cotta.system.dto.request.AddProductDto
import dev.diego.cotta.system.dto.request.CartDto
import dev.diego.cotta.system.dto.request.CartProductDto
import dev.diego.cotta.system.dto.request.CartProductUpdateDto
import dev.diego.cotta.system.dto.request.CheckoutDto
import dev.diego.cotta.system.entity.Cart
import dev.diego.cotta.system.entity.Category
import dev.diego.cotta.system.entity.Product
import dev.diego.cotta.system.entity.Sale
import dev.diego.cotta.system.enum.PaymentType
import dev.diego.cotta.system.repository.CartProductRepository
import dev.diego.cotta.system.repository.CartRepository
import dev.diego.cotta.system.repository.CategoryRepository
import dev.diego.cotta.system.repository.ProductRepository
import dev.diego.cotta.system.stubs.DtoStubs
import dev.diego.cotta.system.stubs.DtoStubs.buildCartProductDto
import dev.diego.cotta.system.stubs.EntitiesStubs.buildCategory
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
import java.time.LocalDate
import java.util.*

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration
class CartControllerTest {
    @Autowired
    private lateinit var cartRepository: CartRepository

    @Autowired
    private lateinit var cartProductRepository: CartProductRepository

    @Autowired
    private lateinit var productRepository: ProductRepository

    @Autowired
    private lateinit var categoryRepository: CategoryRepository

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    lateinit var category: Category
    lateinit var product1: Product
    lateinit var product2: Product

    @BeforeEach
    fun setup() {
        cartProductRepository.deleteAll()
        cartRepository.deleteAll()
        productRepository.deleteAll()
        categoryRepository.deleteAll()

    }

    @AfterEach
    fun tearDown() {
        cartProductRepository.deleteAll()
        cartRepository.deleteAll()
        productRepository.deleteAll()
        categoryRepository.deleteAll()
    }

    companion object {
        const val URL: String = "/cart"
    }

    @Test
    fun `should create cart and return status 200`() {
        //given
        category = categoryRepository.save(buildCategory(id = null))
        product1 = productRepository.save(DtoStubs.buildProductDto(name = "product 1").toEntity(category))
        product2 = productRepository.save(DtoStubs.buildProductDto(name = "product 2").toEntity(category))
        val cartDto: CartDto = DtoStubs.buildCartDto(
            listOf(buildCartProductDto(product1.id!!), buildCartProductDto(product2.id!!))
        )
        val valueAsString: String = objectMapper.writeValueAsString(cartDto)
        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(valueAsString)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.cartCode").exists())
    }

    @Test
    fun `should not save cart and return status 409`() {
        //given
        category = categoryRepository.save(buildCategory(id = null))
        product1 = productRepository.save(DtoStubs.buildProductDto(name = "product 1").toEntity(category))
        val cartDto: CartDto = DtoStubs.buildCartDto(
            listOf(buildCartProductDto(product1.id!!), buildCartProductDto(product1.id!!))
        )
        val valueAsString: String = objectMapper.writeValueAsString(cartDto)
        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(valueAsString)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun `should not save cart and return status 400 when field is not valid on request`() {
        //given
        val cartDto: CartDto = DtoStubs.buildCartDto()
        val valueAsString: String = objectMapper.writeValueAsString(cartDto)
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
    fun `should finish cart and return status 200`() {
        //given
        category = categoryRepository.save(buildCategory(id = null))
        product1 = productRepository.save(DtoStubs.buildProductDto(name = "product 1").toEntity(category))
        val cart = cartRepository.save(Cart())
        cartProductRepository.saveCartProducts(
            cartID = cart.id!!,
            productId = product1.id!!,
            quantity = BigDecimal.ONE,
            price = null
        )
        val checkoutDto = CheckoutDto(cartId = cart.id, paymentType = PaymentType.CREDIT, couponCode = null)
        val valueAsString: String = objectMapper.writeValueAsString(checkoutDto)
        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.post("$URL/checkout")
                .contentType(MediaType.APPLICATION_JSON)
                .content(valueAsString)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
    }

    @Test
    fun `should not finish cart and return status 400 when field is not valid on request`() {
        //given
        category = categoryRepository.save(buildCategory(id = null))
        product1 = productRepository.save(DtoStubs.buildProductDto(name = "product 1").toEntity(category))
        val cart = cartRepository.save(Cart())
        cartProductRepository.saveCartProducts(
            cartID = cart.id!!,
            productId = product1.id!!,
            quantity = BigDecimal.ONE,
            price = null
        )
        val checkoutDto = CheckoutDto(cartId = null, paymentType = PaymentType.CREDIT, couponCode = null)
        val valueAsString: String = objectMapper.writeValueAsString(checkoutDto)
        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.post("$URL/checkout")
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
    fun `should add product on cart and return status 200`() {
        //given
        category = categoryRepository.save(buildCategory(id = null))
        product1 = productRepository.save(DtoStubs.buildProductDto(name = "product 1").toEntity(category))
        product2 = productRepository.save(DtoStubs.buildProductDto(name = "product 2").toEntity(category))
        val cart = cartRepository.save(Cart())
        cartProductRepository.saveCartProducts(
            cartID = cart.id!!,
            productId = product1.id!!,
            quantity = BigDecimal.ONE,
            price = null
        )
        val addProductDto = AddProductDto(cartId = cart.id!!, productId = product2.id!!, quantity = BigDecimal.TEN)
        val valueAsString: String = objectMapper.writeValueAsString(addProductDto)
        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.put("$URL/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(valueAsString)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.cartCode").exists())
    }

    @Test
    fun `should not add product on cart and return status 400 when product already exists`() {
        //given
        category = categoryRepository.save(buildCategory(id = null))
        product1 = productRepository.save(DtoStubs.buildProductDto(name = "product 1").toEntity(category))
        val cart = cartRepository.save(Cart())
        cartProductRepository.saveCartProducts(
            cartID = cart.id!!,
            productId = product1.id!!,
            quantity = BigDecimal.ONE,
            price = null
        )
        val addProductDto = AddProductDto(cartId = cart.id!!, productId = product1.id!!, quantity = BigDecimal.TEN)
        val valueAsString: String = objectMapper.writeValueAsString(addProductDto)
        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.put("$URL/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(valueAsString)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
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
    fun `should not add product on cart and return status 400 when product not exists`() {
        //given
        category = categoryRepository.save(buildCategory(id = null))
        product1 = productRepository.save(DtoStubs.buildProductDto(name = "product 1").toEntity(category))
        val cart = cartRepository.save(Cart())
        cartProductRepository.saveCartProducts(
            cartID = cart.id!!,
            productId = product1.id!!,
            quantity = BigDecimal.ONE,
            price = null
        )
        val addProductDto = AddProductDto(
            cartId = cart.id!!,
            productId = Random().nextLong(),
            quantity = BigDecimal.TEN
        )
        val valueAsString: String = objectMapper.writeValueAsString(addProductDto)
        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.put("$URL/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(valueAsString)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Bad Request! Consult the documentation"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.timestamp").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
            .andExpect(
                MockMvcResultMatchers.jsonPath("$.exception")
                    .value("class org.springframework.dao.DataIntegrityViolationException")
            )
            .andExpect(MockMvcResultMatchers.jsonPath("$.details[*]").isNotEmpty)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `should update product's cart and return status 200`() {
        //given
        category = categoryRepository.save(buildCategory(id = null))
        product1 = productRepository.save(DtoStubs.buildProductDto(name = "product 1").toEntity(category))
        val cart = cartRepository.save(Cart())
        cartProductRepository.saveCartProducts(
            cartID = cart.id!!,
            productId = product1.id!!,
            quantity = BigDecimal.ONE,
            price = null
        )
        val cartProductUpdateDto = CartProductUpdateDto(
            cartId = cart.id!!,
            products = listOf(
                CartProductDto(
                    productId = product1.id!!,
                    quantity = BigDecimal.TEN
                )
            )
        )
        val valueAsString: String = objectMapper.writeValueAsString(cartProductUpdateDto)
        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.put("$URL/update-product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(valueAsString)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.cartCode").exists())
    }

    @Test
    fun `should not update product's cart and return status 400 when cartid not exist's`() {
        //given
        category = categoryRepository.save(buildCategory(id = null))
        product1 = productRepository.save(DtoStubs.buildProductDto(name = "product 1").toEntity(category))
        val cart = cartRepository.save(Cart())
        cartProductRepository.saveCartProducts(
            cartID = cart.id!!,
            productId = product1.id!!,
            quantity = BigDecimal.ONE,
            price = null
        )
        val cartProductUpdateDto = CartProductUpdateDto(
            cartId = UUID.randomUUID(),
            products = listOf(
                CartProductDto(
                    productId = product1.id!!,
                    quantity = BigDecimal.TEN
                )
            )
        )
        val valueAsString: String = objectMapper.writeValueAsString(cartProductUpdateDto)
        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.put("$URL/update-product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(valueAsString)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
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
    fun `should not update product's cart and return status 400 when productId not exist's`() {
        //given
        category = categoryRepository.save(buildCategory(id = null))
        product1 = productRepository.save(DtoStubs.buildProductDto(name = "product 1").toEntity(category))
        val cart = cartRepository.save(Cart())
        cartProductRepository.saveCartProducts(
            cartID = cart.id!!,
            productId = product1.id!!,
            quantity = BigDecimal.ONE,
            price = null
        )
        val cartProductUpdateDto = CartProductUpdateDto(
            cartId = cart.id!!,
            products = listOf(
                CartProductDto(
                    productId = Random().nextLong(),
                    quantity = BigDecimal.TEN
                )
            )
        )
        val valueAsString: String = objectMapper.writeValueAsString(cartProductUpdateDto)
        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.put("$URL/update-product")
                .contentType(MediaType.APPLICATION_JSON)
                .content(valueAsString)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
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
    fun `should return cart and return status 200`() {
        //given
        category = categoryRepository.save(buildCategory(id = null))
        product1 = productRepository.save(DtoStubs.buildProductDto(name = "product 1").toEntity(category))
        val cart = cartRepository.save(Cart())
        cartProductRepository.saveCartProducts(
            cartID = cart.id!!,
            productId = product1.id!!,
            quantity = BigDecimal.ONE,
            price = null
        )
        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.get(URL)
                .queryParam("id", cart.id!!.toString())
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.cartId").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.totalProducts").exists())
    }


    @Test
    fun `should return list of cart's and return status 200`() {
        //given
        category = categoryRepository.save(buildCategory(id = null))
        product1 = productRepository.save(DtoStubs.buildProductDto(name = "product 1").toEntity(category))
        val cart = cartRepository.save(
            Cart(
                sale = Sale(
                    totalPrice = BigDecimal.valueOf(54),
                    date = LocalDate.now(),
                    paymentType = PaymentType.CREDIT
                )
            )
        )
        cartProductRepository.saveCartProducts(
            cartID = cart.id!!,
            productId = product1.id!!,
            quantity = BigDecimal.ONE,
            price = null
        )
        val cartProductUpdateDto = CartProductUpdateDto(
            cartId = cart.id!!,
            products = listOf(
                CartProductDto(
                    productId = product1.id!!,
                    quantity = BigDecimal.TEN
                )
            )
        )
        val valueAsString: String = objectMapper.writeValueAsString(cartProductUpdateDto)
        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.get("$URL/today-sales")
                .contentType(MediaType.APPLICATION_JSON)
                .content(valueAsString)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$").isArray)
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].date").value(LocalDate.now().toString()))
    }

    @Test
    fun `should delete product from cart and return status 200`() {
        //given
        category = categoryRepository.save(buildCategory(id = null))
        product1 = productRepository.save(DtoStubs.buildProductDto(name = "product 1").toEntity(category))
        val cart = cartRepository.save(Cart())
        cartProductRepository.saveCartProducts(
            cartID = cart.id!!,
            productId = product1.id!!,
            quantity = BigDecimal.ONE,
            price = null
        )
        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders
                .delete("$URL/remove/product/${product1.id!!}/cart/${cart.id!!}")
        )
            .andExpect(MockMvcResultMatchers.status().isNoContent)

    }

    @Test
    fun `should not delete product from cart and return status 400`() {
        //given
        category = categoryRepository.save(buildCategory(id = null))
        product1 = productRepository.save(DtoStubs.buildProductDto(name = "product 1").toEntity(category))
        val cart = cartRepository.save(
            Cart(
                sale = Sale(
                    totalPrice = BigDecimal.valueOf(54),
                    date = LocalDate.now(),
                    paymentType = PaymentType.CREDIT
                )
            )
        )
        cartProductRepository.saveCartProducts(
            cartID = cart.id!!,
            productId = product1.id!!,
            quantity = BigDecimal.ONE,
            price = null
        )
        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders
                .delete("$URL/remove/product/${product1.id!!}/cart/${cart.id!!}")
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
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

}
