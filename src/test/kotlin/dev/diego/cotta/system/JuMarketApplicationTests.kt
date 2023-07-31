package dev.diego.cotta.system

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class JuMarketApplicationTests {

    @Test
    fun contextLoads() {
        assertTrue(true)
    }

}
