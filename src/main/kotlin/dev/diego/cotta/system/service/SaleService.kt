package dev.diego.cotta.system.service

import dev.diego.cotta.system.entity.Sale
import java.util.*

interface SaleService {

    fun save(sale: Sale): Sale

    fun findSaleById(saleId: UUID): Sale

    fun findAllByTodayDate(): List<Sale>

}