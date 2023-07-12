package dev.diego.cotta.system

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class JuMarketApplication

fun main(args: Array<String>) {
	runApplication<JuMarketApplication>(*args)
}
