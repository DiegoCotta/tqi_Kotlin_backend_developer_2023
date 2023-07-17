package dev.diego.cotta.system.entity

import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "cart")
class Cart(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,
    @OneToMany(mappedBy = "cart") var products: List<CartProduct> = mutableListOf(),
    @Embedded var sale: Sale? = null
)
