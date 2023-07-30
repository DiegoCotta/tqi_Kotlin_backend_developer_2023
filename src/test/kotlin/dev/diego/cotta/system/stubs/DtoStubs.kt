package dev.diego.cotta.system.stubs

import dev.diego.cotta.system.dto.request.CartDto
import dev.diego.cotta.system.dto.request.CartProductDto
import dev.diego.cotta.system.dto.request.CategoryDto
import dev.diego.cotta.system.dto.request.CouponDto
import dev.diego.cotta.system.dto.request.ProductDto
import dev.diego.cotta.system.enum.CouponType
import dev.diego.cotta.system.enum.MeasuringUnitType
import java.math.BigDecimal
import java.time.LocalDate

object DtoStubs {

    fun buildCouponDto(
        code: String = "code",
        discountType: CouponType = CouponType.PERCENTAGE,
        discountValue: BigDecimal = BigDecimal.TEN,
        expirationDate: LocalDate = LocalDate.now()
    ) =
        CouponDto(
            code = code,
            discountType = discountType,
            discountValue = discountValue,
            expirationDate = expirationDate
        )

    fun buildCategoryDto(name: String = "Category Name") = CategoryDto(name = name)
    fun buildProductDto(
        name: String = "produto",
        measuringUnit: MeasuringUnitType = MeasuringUnitType.CT,
        price: BigDecimal = BigDecimal.ONE,
        categoryId: Long = 1L
    ) =
        ProductDto(name = name,
            measuringUnit = measuringUnit,
            price = price,
            categoryId = categoryId
        )

    fun buildCartDto(
        products: List<CartProductDto> = listOf()
    ) = CartDto(
        products = products
    )

    fun buildCartProductDto(
        id: Long = 1L,
        quantity: BigDecimal = BigDecimal.ONE
    ) = CartProductDto(
        productId = id,
        quantity = quantity
    )
}
