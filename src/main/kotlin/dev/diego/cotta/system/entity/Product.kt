package dev.diego.cotta.system.entity


import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "product")
class Product(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(nullable = false) val name: String = "",
    @Enumerated @Column(nullable = false, length = 2)
    val measuringUnit: MeasuringUnitType = MeasuringUnitType.UN,
    @Column(nullable = false) var price: BigDecimal = BigDecimal.ZERO,
    @Column(nullable = false) var quantity: BigDecimal = BigDecimal.ZERO,
    @ManyToOne @JoinColumn(name = "category_id", referencedColumnName = "id")
    var category: Category,
    @OneToMany(mappedBy = "product")
    var carts: List<CartProduct> = listOf()
)
