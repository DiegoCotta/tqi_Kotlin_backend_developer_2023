package dev.diego.cotta.system.exception

data class BusinessException(override val message: String?) : RuntimeException(message)
