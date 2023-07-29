package dev.diego.cotta.system.stubs

import dev.diego.cotta.system.entity.Cart
import dev.diego.cotta.system.entity.CartProduct
import dev.diego.cotta.system.entity.CartProductId
import dev.diego.cotta.system.entity.Category
import dev.diego.cotta.system.entity.Coupon
import dev.diego.cotta.system.entity.Product
import dev.diego.cotta.system.entity.Sale
import dev.diego.cotta.system.entity.User
import dev.diego.cotta.system.enum.CouponType
import dev.diego.cotta.system.enum.MeasuringUnitType
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

object Stubs {
    fun buildCoupon(
        id: Long? = 1L,
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

    fun buildProduct(
        id: Long? = 1L,
        name: String = "Detergente",
        measuringUnit: MeasuringUnitType = MeasuringUnitType.UN,
        price: BigDecimal = BigDecimal.valueOf(6.5),
        category: Category = Category(
            id = 1L,
            name = "Limpeza"
        )
    ) = Product(
        id = id,
        name = name,
        measuringUnit = measuringUnit,
        price = price,
        category = category
    )

    fun buildCategory(
        id: Long? = 1L,
        name: String = "Bombonier"
    ) = Category(id = id, name = name)

    fun buildUser(
        id: Int? = 1,
        name: String = "name",
        username: String = "username",
        password: String = "password123",
        roles: List<String> = ArrayList()
    ) = User(
        id = id,
        name = name,
        username = username,
        password = password,
        roles = roles
    )

    fun buildCart(
        id: UUID? = UUID.randomUUID(),
        sale: Sale = Sale(
            coupon = null,
            paymentType = null,
            totalPriceWithDiscount = null,
            date = null,
        )
    ) = Cart(
        id = id,
        sale = sale
    )

    fun buildCartProduct(
        cartProductId: CartProductId = CartProductId(cartId = UUID.randomUUID(), productId = 1L),
        quantity: BigDecimal = BigDecimal.valueOf(3.0),
        price: BigDecimal = BigDecimal.valueOf(13.0),
        cart: Cart? = null,
        product: Product? = null
    ) = CartProduct(
        cartProductId = cartProductId,
        quantity = quantity,
        price = price,
        cart = cart,
        product = product
    )
}
