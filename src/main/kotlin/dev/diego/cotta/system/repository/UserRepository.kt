package dev.diego.cotta.system.repository

import dev.diego.cotta.system.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, UUID> {
    fun findUserByUsername( username: String?): User?
}
