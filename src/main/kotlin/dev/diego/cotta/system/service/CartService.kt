package dev.diego.cotta.system.service

import dev.diego.cotta.system.entity.Cart
import java.util.*

interface CartService {

    fun save(cart: Cart): Cart

    fun findCartById(id: UUID): Cart


}