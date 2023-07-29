package dev.diego.cotta.system.controller

import com.fasterxml.jackson.databind.ObjectMapper
import dev.diego.cotta.system.dto.request.CouponDto
import dev.diego.cotta.system.repository.CouponRepository
import dev.diego.cotta.system.stubs.DtoStubs.buildCouponDto
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
import java.time.LocalDate

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration
class CouponControllerTest {
    @Autowired
    private lateinit var couponRepository: CouponRepository

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper


    @BeforeEach
    fun setup() = couponRepository.deleteAll()

    @AfterEach
    fun tearDown() = couponRepository.deleteAll()

    companion object {
        const val URL: String = "/coupon"
    }

    @Test
    fun `should create coupon and return status 201`() {
        //given
        val couponDto: CouponDto = buildCouponDto(expirationDate = LocalDate.now().plusDays(1))
        val valueAsString: String = objectMapper.writeValueAsString(couponDto)
        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(valueAsString)
        ).andExpect(MockMvcResultMatchers.status().isCreated)
    }

    @Test
    fun `should not save coupon and return status 409`() {
        //given
        couponRepository.save(buildCouponDto().toEntity())
        val couponDto: CouponDto = buildCouponDto()
        val valueAsString: String = objectMapper.writeValueAsString(couponDto)
        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(valueAsString)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun `should not save coupon and return status 400 when field is not valid on request`() {
        //given
        val couponDto: CouponDto = buildCouponDto(code = "")
        val valueAsString: String = objectMapper.writeValueAsString(couponDto)
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
    fun `should return list of coupon's and status 200`() {
        //given
        couponRepository.save(buildCouponDto().toEntity())
        //when
        //then
        mockMvc.perform(
            MockMvcRequestBuilders.get(URL)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$").isArray)
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].code").exists())
    }
}
