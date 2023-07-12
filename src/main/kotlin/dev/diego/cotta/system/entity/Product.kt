package dev.diego.cotta.system.entity

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "product")
data class Product(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,
        @Column(nullable = false) val name: String = "",
        @Column(nullable = false, length = 2) val measuringUnit: String = "",
        @Column(nullable = false) var price: BigDecimal = BigDecimal.ZERO,
        @Column(nullable = false) var quantity: BigDecimal = BigDecimal.ZERO,
        @ManyToOne @JoinColumn(name = "category_id", referencedColumnName = "id")
        var category: Category,
        @ManyToMany(mappedBy = "products")
        var carts: List<Cart> = listOf()
)
