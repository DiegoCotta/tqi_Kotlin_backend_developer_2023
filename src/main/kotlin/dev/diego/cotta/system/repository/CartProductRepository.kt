package dev.diego.cotta.system.repository

import dev.diego.cotta.system.entity.Cart
import dev.diego.cotta.system.entity.CartProduct
import dev.diego.cotta.system.entity.CartProductId
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.math.BigDecimal
import java.util.UUID


interface CartProductRepository : JpaRepository<CartProduct, CartProductId> {

    @Modifying
    @Query(value = "insert into cart_product (CART_ID,PRODUCT_ID,QUANTITY,PRICE)" +
        " VALUES (:cartId,:productId, :quantity, :price)", nativeQuery = true)
    @Transactional
    fun saveCartProducts(@Param("cartId") cartID: UUID, @Param("productId") productId: Long,
                         @Param("quantity") quantity: BigDecimal, @Param("price") price: BigDecimal?)

    @Transactional
    @Modifying
    @Query("update cart_product set PRICE = :price where PRODUCT_ID = " +
        ":productId and CART_ID = :cartId", nativeQuery = true)
    fun updateCartProductPrice(@Param("cartId") cartID: UUID, @Param("productId") productId: Long,
                               @Param("price") price: BigDecimal?)

}
