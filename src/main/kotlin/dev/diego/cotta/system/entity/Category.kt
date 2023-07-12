package dev.diego.cotta.system.entity

import jakarta.persistence.*

@Entity
@Table(name = "category")
data class Category(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        @Column(nullable = false)
        val name: String = ""
)