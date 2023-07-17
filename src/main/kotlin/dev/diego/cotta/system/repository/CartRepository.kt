package dev.diego.cotta.system.repository

import dev.diego.cotta.system.entity.Cart
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate
import java.util.UUID

interface CartRepository : JpaRepository<Cart, UUID> {

    @SuppressWarnings("FunctionNaming")
    fun findAllBySale_Date(date: LocalDate): List<Cart>
}
