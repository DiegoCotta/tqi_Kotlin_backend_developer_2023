package dev.diego.cotta.system.repository

import dev.diego.cotta.system.entity.Coupon
import dev.diego.cotta.system.stubs.Stubs.buildCoupon
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
class CouponRepositoryTest {

    @Autowired
    lateinit var couponRepository: CouponRepository

    @Autowired
    lateinit var testEntityManager: TestEntityManager

    private lateinit var coupon: Coupon

    @BeforeEach
    fun setup() {
        coupon = testEntityManager.persist(buildCoupon(id = null, code = "codeTest"))
    }

    @Test
    fun `should find coupon by code`() {
        //given
        val couponCode = "codeTest"

        //when
        val response: Coupon = couponRepository.findByCode(couponCode)!!
        //then
        Assertions.assertThat(response).isNotNull
        Assertions.assertThat(coupon).isSameAs(response)
    }

    @Test
    fun `should not find coupon by code`() {
        //given
        val couponCode = "error"

        //when
        val response: Coupon? = couponRepository.findByCode(couponCode)
        //then
        Assertions.assertThat(response).isNull()
    }

}



