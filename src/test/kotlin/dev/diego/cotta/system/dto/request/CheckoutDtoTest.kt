package dev.diego.cotta.system.dto.request


import dev.diego.cotta.system.entity.Cart
import dev.diego.cotta.system.entity.CartProduct
import dev.diego.cotta.system.entity.CartProductId
import dev.diego.cotta.system.entity.Category
import dev.diego.cotta.system.entity.Coupon
import dev.diego.cotta.system.entity.Product
import dev.diego.cotta.system.entity.Sale
import dev.diego.cotta.system.enum.CouponType
import dev.diego.cotta.system.enum.MeasuringUnitType
import dev.diego.cotta.system.enum.PaymentType
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

class CheckoutDtoTest {

    @Test
    fun `toEntity with null coupon`() {
        //given
        val checkoutDto = CheckoutDto(
            cartId = UUID.randomUUID(),
            couponCode = null,
            paymentType = PaymentType.CREDIT
        )
        val cart = Cart(
            id = checkoutDto.cartId,
            products = mutableListOf(
                CartProduct(
                    cartProductId = CartProductId(checkoutDto.cartId!!, 1L),
                    quantity = BigDecimal.valueOf(2),
                    product = Product(
                        1L,
                        "product",
                        MeasuringUnitType.PC,
                        category = Category(1L, "category"),
                        price = BigDecimal.TEN
                    )
                )
            )
        )
        val expectedEntity = Sale(
            coupon = null,
            paymentType = PaymentType.CREDIT,
            totalPriceWithDiscount = null,
            date = LocalDate.now(),
            totalPrice = BigDecimal.TEN.multiply(BigDecimal.valueOf(2))
        )
        //when
        val response = checkoutDto.toEntity(cart = cart, coupon = null)
        //then
        Assertions.assertThat(response).usingRecursiveComparison()
            .ignoringFields("fieldToIgnore")
            .isEqualTo(expectedEntity)
    }

    @Test
    fun `toEntity with coupon fixed discount`() {
        //given
        val checkoutDto = CheckoutDto(
            cartId = UUID.randomUUID(),
            couponCode = "code",
            paymentType = PaymentType.CREDIT
        )
        val cart = Cart(
            id = checkoutDto.cartId,
            products = mutableListOf(
                CartProduct(
                    cartProductId = CartProductId(checkoutDto.cartId!!, 1L),
                    quantity = BigDecimal.valueOf(2),
                    product = Product(
                        1L,
                        "product",
                        MeasuringUnitType.PC,
                        category = Category(1L, "category"),
                        price = BigDecimal.TEN
                    )
                )
            )
        )
        val expectedEntity = Sale(
            coupon = Coupon(1L, "code", CouponType.FIXED, BigDecimal.valueOf(5), LocalDate.now()),
            paymentType = PaymentType.CREDIT,
            totalPriceWithDiscount = BigDecimal.TEN.multiply(BigDecimal.valueOf(2)).minus(BigDecimal.valueOf(5)),
            date = LocalDate.now(),
            totalPrice = BigDecimal.TEN.multiply(BigDecimal.valueOf(2))
        )
        //when
        val response = checkoutDto.toEntity(cart = cart,
            coupon = Coupon(
                1L,
                "code",
                CouponType.FIXED,
                BigDecimal.valueOf(5),
                LocalDate.now())
        )
        //then
        Assertions.assertThat(response).usingRecursiveComparison()
            .ignoringFields("fieldToIgnore")
            .isEqualTo(expectedEntity)
    }

    @Test
    fun `toEntity with coupon fixed discount and greater than price`() {
        //given
        val checkoutDto = CheckoutDto(
            cartId = UUID.randomUUID(),
            couponCode = "code",
            paymentType = PaymentType.CREDIT
        )
        val cart = Cart(
            id = checkoutDto.cartId,
            products = mutableListOf(
                CartProduct(
                    cartProductId = CartProductId(checkoutDto.cartId!!, 1L),
                    quantity = BigDecimal.ONE,
                    product = Product(
                        1L,
                        "product",
                        MeasuringUnitType.PC,
                        category = Category(1L, "category"),
                        price = BigDecimal.TEN
                    )
                )
            )
        )
        val expectedEntity = Sale(
            coupon = Coupon(1L, "code", CouponType.FIXED, BigDecimal.valueOf(15), LocalDate.now()),
            paymentType = PaymentType.CREDIT,
            totalPriceWithDiscount = BigDecimal.ZERO,
            date = LocalDate.now(),
            totalPrice = BigDecimal.TEN
        )
        //when
        val response = checkoutDto.toEntity(cart = cart,
            coupon = Coupon(1L, "code", CouponType.FIXED, BigDecimal.valueOf(15), LocalDate.now())
        )
        //then
        Assertions.assertThat(response).usingRecursiveComparison()
            .ignoringFields("fieldToIgnore")
            .isEqualTo(expectedEntity)
    }

    @Test
    fun `toEntity with coupon percentage discount`() {
        //given
        val checkoutDto = CheckoutDto(
            cartId = UUID.randomUUID(),
            couponCode = "code",
            paymentType = PaymentType.CREDIT
        )
        val cart = Cart(
            id = checkoutDto.cartId,
            products = mutableListOf(
                CartProduct(
                    cartProductId = CartProductId(checkoutDto.cartId!!, 1L),
                    quantity = BigDecimal.valueOf(2),
                    product = Product(
                        1L,
                        "product",
                        MeasuringUnitType.PC,
                        category = Category(1L, "category"),
                        price = BigDecimal.TEN
                    )
                )
            )
        )
        val expectedEntity = Sale(
            coupon = Coupon(1L, "code", CouponType.PERCENTAGE, BigDecimal.valueOf(10), LocalDate.now()),
            paymentType = PaymentType.CREDIT,
            totalPriceWithDiscount = BigDecimal.TEN.multiply(BigDecimal.valueOf(0.9)).multiply(BigDecimal.valueOf(2)),
            date = LocalDate.now(),
            totalPrice = BigDecimal.TEN.multiply(BigDecimal.valueOf(2))
        )
        //when
        val response = checkoutDto.toEntity(cart = cart,
            coupon = Coupon(1L, "code", CouponType.PERCENTAGE, BigDecimal.valueOf(10), LocalDate.now())
        )
        //then
        Assertions.assertThat(response).usingRecursiveComparison()
            .ignoringFields("fieldToIgnore")
            .isEqualTo(expectedEntity)
    }
}
