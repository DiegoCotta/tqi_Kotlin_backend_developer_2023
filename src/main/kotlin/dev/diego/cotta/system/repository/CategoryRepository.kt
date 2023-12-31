package dev.diego.cotta.system.repository

import dev.diego.cotta.system.entity.Category
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<Category, Long>
