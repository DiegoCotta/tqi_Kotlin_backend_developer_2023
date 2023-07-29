package dev.diego.cotta.system.dto.response

import dev.diego.cotta.system.entity.Coupon
import dev.diego.cotta.system.enum.CouponType
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDate

class CouponViewTest {

    @Test
    fun `secondaryConstructor with coupontype percentage`() {
        //given
        val couponView = CouponView(
            Coupon(1L, "code", CouponType.PERCENTAGE, BigDecimal.ONE, LocalDate.EPOCH)
        )
        val expected = CouponView(
            code = "code", discount = "1%", expirationDate = "1970-01-01"
        )
        //when
        //then
        Assertions.assertThat(couponView).usingRecursiveComparison()
            .ignoringFields("fieldToIgnore")
            .isEqualTo(expected)
    }

    @Test
    fun `secondaryConstructor with coupontype fixed`() {
        //given
        val couponView = CouponView(
            Coupon(1L, "code", CouponType.FIXED, BigDecimal.ONE, LocalDate.EPOCH)
        )
        val expected = CouponView(
            code = "code", discount = "R$ 1,00", expirationDate = "1970-01-01"
        )
        //when
        //then
        Assertions.assertThat(couponView).usingRecursiveComparison()
            .ignoringFields("fieldToIgnore")
            .isEqualTo(expected)
    }
}
