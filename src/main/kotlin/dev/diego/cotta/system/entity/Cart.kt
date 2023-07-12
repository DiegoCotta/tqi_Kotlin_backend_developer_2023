package dev.diego.cotta.system.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "cart")
data class Cart(
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        val id: UUID? = null,
        @Column(nullable = false) val amountItems: Int = 0,
        @ManyToMany @JoinTable(
                name = "cart_product",
                joinColumns = [JoinColumn(name = "product_id", referencedColumnName = "id")],
                inverseJoinColumns = [JoinColumn(name = "cart_id", referencedColumnName = "id")],
        ) var products: List<Product> = mutableListOf(),
        @OneToOne @JoinColumn(name = "sale_id", referencedColumnName = "id") val sale: Sale?
)