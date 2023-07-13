package dev.diego.cotta.system.repository

import dev.diego.cotta.system.entity.Cart
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface CartRepository : JpaRepository<Cart, UUID>