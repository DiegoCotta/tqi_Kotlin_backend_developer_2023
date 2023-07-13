package dev.diego.cotta.system.service.impl

import dev.diego.cotta.system.entity.Cart
import dev.diego.cotta.system.repository.CartRepository
import dev.diego.cotta.system.service.CartService
import org.springframework.stereotype.Service
import java.util.*

@Service
class CartServiceImpl(val repository: CartRepository) : CartService {
    override fun save(cart: Cart): Cart =
        repository.save(cart)

    override fun findCartById(id: UUID): Cart =
        repository.findById(id).orElseThrow { throw RuntimeException("Carrinho não encontrado!") }
}