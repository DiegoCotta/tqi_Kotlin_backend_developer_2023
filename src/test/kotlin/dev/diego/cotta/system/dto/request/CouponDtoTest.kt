package dev.diego.cotta.system.dto.request

import dev.diego.cotta.system.entity.Coupon
import dev.diego.cotta.system.enum.CouponType
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDate

class CouponDtoTest {

    @Test
    fun toEntity() {
        //given
        val couponDto = CouponDto(
            code = "code",
            discountType = CouponType.PERCENTAGE,
            discountValue = BigDecimal.valueOf(15),
            expirationDate = LocalDate.MAX
        )
        val expectedEntity = Coupon(
            code = "code",
            discountType = CouponType.PERCENTAGE,
            discountValue = BigDecimal.valueOf(15),
            expirationDate = LocalDate.MAX
        )
        //when
        val entity = couponDto.toEntity()
        //then
        Assertions.assertThat(entity).usingRecursiveComparison()
            .ignoringFields("fieldToIgnore")
            .isEqualTo(expectedEntity)
    }
}
