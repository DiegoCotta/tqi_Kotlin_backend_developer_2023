package dev.diego.cotta.system.init

import dev.diego.cotta.system.entity.User
import dev.diego.cotta.system.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component


@Component
class StartApplication(private val userRepository: UserRepository) : CommandLineRunner {
    @Autowired
    private val passwordEncoder: PasswordEncoder? = null
    override fun run(vararg args: String?) {
        var user: User? = userRepository.findByUsername("admin")
        if (user == null) {
            user = User(username = "admin", name = "admin", password = passwordEncoder?.encode("master123"),
                roles = listOf("MANAGERS"))
            userRepository.save(user)
        }
    }
}
