package dev.diego.cotta.system.entity


import jakarta.persistence.Column
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.MapsId
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "cart_product")
class CartProduct(

    @EmbeddedId
    val cartProductId: CartProductId,
    @MapsId("cartId")
    @ManyToOne @JoinColumn(name = "cartId", updatable = false, nullable = false)
    val cart: Cart? = null,
    @MapsId("productId")
    @ManyToOne @JoinColumn(name = "productId", updatable = false, nullable = false)
    val product: Product? = null,
    @Column(nullable = false, precision = 19, scale = 2)
    var quantity: BigDecimal
)
