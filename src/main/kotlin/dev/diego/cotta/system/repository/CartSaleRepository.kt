package dev.diego.cotta.system.repository;

import dev.diego.cotta.system.entity.Cart
import dev.diego.cotta.system.entity.CartSale
import org.springframework.data.jpa.repository.JpaRepository

interface CartSaleRepository : JpaRepository<CartSale, Cart>