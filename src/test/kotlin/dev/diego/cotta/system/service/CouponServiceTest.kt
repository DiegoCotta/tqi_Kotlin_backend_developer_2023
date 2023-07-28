package dev.diego.cotta.system.service

import dev.diego.cotta.system.entity.Coupon
import dev.diego.cotta.system.repository.CouponRepository
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal
import java.time.LocalDate
import dev.diego.cotta.system.enum.CouponType
import dev.diego.cotta.system.exception.BusinessException
import dev.diego.cotta.system.service.impl.CouponServiceImpl
import io.mockk.every
import io.mockk.verify
import org.assertj.core.api.Assertions

@ActiveProfiles("test")
@ExtendWith(MockKExtension::class)
class CouponServiceTest {

    @MockK
    lateinit var couponRepository: CouponRepository

    @InjectMockKs
    lateinit var couponService: CouponServiceImpl

    @Test
    fun `CouponService save should create coupon`() {
        //given
        val fakeCoupon: Coupon = buildCoupon()
        every { couponRepository.save(any()) } returns fakeCoupon
        //when
        val savedCoupon = couponService.save(fakeCoupon)
        //then

        Assertions.assertThat(savedCoupon).isSameAs(fakeCoupon)
        verify { couponRepository.save(any()) }
    }

    @Test
    fun `CouponService listCoupons should return list of Coupon`() {
        //given
        val fakeListCoupon: List<Coupon> = listOf(buildCoupon(), buildCoupon(discountType = CouponType.PERCENTAGE))
        every { couponRepository.findAll() } returns fakeListCoupon

        //when
        val couponList = couponService.listCoupons()
        //then

        Assertions.assertThat(couponList).isSameAs(fakeListCoupon)
        verify { couponRepository.findAll() }
    }

    @Test
    fun `CouponService findByCode success should return list of Coupon`() {
        //given
        val fakeCoupon: Coupon = buildCoupon()
        every { couponRepository.findByCode(any()) } returns fakeCoupon

        //when
        val coupon = couponService.findByCode("teste")
        //then

        Assertions.assertThat(coupon).isSameAs(fakeCoupon)
        verify { couponRepository.findByCode("teste") }
    }

    @Test
    fun `CouponService findByCode error should return BusinessException`() {
        //given

        every { couponRepository.findByCode(any()) } returns null

        //when
        //then
        Assertions.assertThatExceptionOfType(BusinessException::class.java)
            .isThrownBy { couponService.findByCode("teste") }
            .withMessage("Cupom Inválido!")
        verify { couponRepository.findByCode("teste") }
    }

    companion object {
        private fun buildCoupon(
            id: Long = 1L,
            code: String = "teste",
            discountType: CouponType = CouponType.FIXED,
            discountValue: BigDecimal = BigDecimal.valueOf(10.0),
            expirationDate: LocalDate = LocalDate.now()
        ): Coupon = Coupon(
            id = id,
            code = code,
            discountType = discountType,
            discountValue = discountValue,
            expirationDate = expirationDate
        )
    }

}
