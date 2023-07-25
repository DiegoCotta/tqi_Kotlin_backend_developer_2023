package dev.diego.cotta.system

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder


@SpringBootApplication
class JuMarketApplication{
    @Bean
    fun passwordEncoder(): PasswordEncoder? {
        return BCryptPasswordEncoder()
    }
}

@SuppressWarnings("SpreadOperator")
fun main(args: Array<String>) {
    runApplication<JuMarketApplication>(*args)
}
