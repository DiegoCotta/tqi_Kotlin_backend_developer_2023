package dev.diego.cotta.system.service

import dev.diego.cotta.system.repository.UserRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class SecurityDatabaseService(private val userRepository: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val userEntity = userRepository.findByUsername(username) ?: throw UsernameNotFoundException(username)
        val authorities: MutableSet<GrantedAuthority> = HashSet()
        userEntity.roles.forEach { role -> authorities.add(SimpleGrantedAuthority("ROLE_$role")) }
        return User(userEntity.username,
            userEntity.password,
            authorities)
    }
}
