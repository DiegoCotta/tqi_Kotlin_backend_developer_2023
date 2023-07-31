package dev.diego.cotta.system.security

import dev.diego.cotta.system.service.SecurityDatabaseService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
class WebSecurityConfig(private val userDetailsService: SecurityDatabaseService,
                        private val passwordEncoder: PasswordEncoder) {

    @SuppressWarnings("UnusedPrivateMember")
    private fun authManager(http: HttpSecurity): AuthenticationManager {
        val authenticationManagerBuilder = http.getSharedObject(
            AuthenticationManagerBuilder::class.java
        )
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder)
        return authenticationManagerBuilder.build()
    }

    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain? {
        http.csrf {
            it.disable()
        }.headers {
            it.frameOptions { configurations ->
                configurations.disable()
            }
        }.authorizeHttpRequests { authz ->
            authz
                .requestMatchers(HttpMethod.POST, "/category").hasAnyRole("MANAGERS")
                .requestMatchers(HttpMethod.DELETE, "/category/**").hasAnyRole("MANAGERS")
                .requestMatchers(HttpMethod.GET, "/category").permitAll()
                .requestMatchers(HttpMethod.POST, "/product").hasAnyRole("MANAGERS")
                .requestMatchers(HttpMethod.PUT, "/product").hasAnyRole("MANAGERS")
                .requestMatchers(HttpMethod.GET, "/product/find/id/**").hasAnyRole("MANAGERS")
                .requestMatchers(HttpMethod.GET, "/product",
                    "/product/find/name/**", "/product/find/category/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/cart").permitAll()
                .requestMatchers(HttpMethod.POST, "/cart/checkout").permitAll()
                .requestMatchers(HttpMethod.PUT, "/cart/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/cart/today-sales").hasAnyRole("MANAGERS")
                .requestMatchers(HttpMethod.GET, "/cart").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/cart/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/coupon").hasAnyRole("MANAGERS")
                .anyRequest().authenticated()
        }.httpBasic(Customizer.withDefaults())
        return http.build()
    }

}
