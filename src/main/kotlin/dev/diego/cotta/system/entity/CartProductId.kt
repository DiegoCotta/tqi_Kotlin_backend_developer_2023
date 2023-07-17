package dev.diego.cotta.system.entity

import jakarta.persistence.Embeddable
import java.io.Serializable
import java.util.*

@Embeddable
class CartProductId(
    val cartId: UUID,
    val productId: Long
) : Serializable {
    companion object {
        private const val serialVersionUID = -307144L
    }

}
