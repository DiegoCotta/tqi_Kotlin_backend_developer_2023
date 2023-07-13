package dev.diego.cotta.system.repository

import dev.diego.cotta.system.entity.Product
import dev.diego.cotta.system.entity.Sale
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate
import java.util.*

interface SaleRepository : JpaRepository<Sale, UUID> {

    @Query(value = "SELECT * FROM sale WHERE date <= ?1", nativeQuery = true)
    fun findAllByDateBetween(startDate: LocalDate, endDate: LocalDate): List<Sale>
}