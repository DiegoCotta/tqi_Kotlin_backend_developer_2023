package dev.diego.cotta.system.entity

import jakarta.persistence.*

@Entity
@Table(name = "cart_sale")
data class CartSale(
    @Id @ManyToOne @JoinColumn(name = "cart_id")
    val cart: Cart,
    @Id @ManyToOne @JoinColumn(name = "product_id")
    val product: Product,
    @Column(nullable = false)
    val quantity: Int
)