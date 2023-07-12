package dev.diego.cotta.system.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "cart")
data class Cart(
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        val id: UUID? = null,
        @OneToMany(mappedBy = "cart")  var products: List<CartSale> = mutableListOf(),
        @OneToOne @JoinColumn(name = "sale_id", referencedColumnName = "id") val sale: Sale?
)