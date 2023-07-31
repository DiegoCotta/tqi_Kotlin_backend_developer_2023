package dev.diego.cotta.system.enum

enum class PaymentType(val paymentName: String) {
    CREDIT("Cartão de Crédito"),
    DEBIT("Cartão de Débito"),
    PIX("Pix"),
    MONEY("Dinheiro");
}
