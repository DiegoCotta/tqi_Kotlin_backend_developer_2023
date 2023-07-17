package dev.diego.cotta.system.entity

import jakarta.persistence.Embeddable
import java.io.Serializable
import java.util.UUID

@Embeddable
class CartProductId(
    val cartId: UUID,
    val productId: Long
) : Serializable {
    companion object {
        private const val serialVersionUID = -307144L
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CartProductId

        if (cartId != other.cartId) return false
        return productId == other.productId
    }

    override fun hashCode(): Int {
        var result = cartId.hashCode()
        result = 31 * result + productId.hashCode()
        return result
    }


}
