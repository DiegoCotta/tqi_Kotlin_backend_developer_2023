package dev.diego.cotta.system.service.impl

import dev.diego.cotta.system.entity.Sale
import dev.diego.cotta.system.repository.SaleRepository
import dev.diego.cotta.system.service.SaleService
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

@Service
class SaleServiceImpl(val repository: SaleRepository) : SaleService {
    override fun save(sale: Sale): Sale =
        repository.save(sale)

    override fun findSaleById(saleId: UUID): Sale =
        repository.findById(saleId).orElseThrow { throw RuntimeException("Venda não encontrada!") }

    override fun findAllByTodayDate(): List<Sale> {
        val today = LocalDate.now()
        return repository.findAllByDateBetween(
            today.atStartOfDay().toLocalDate(),
            LocalTime.MAX.atDate(today).toLocalDate()
        )
    }

}