package com.alientodevida.alientoapp.domain.entities.local

sealed class PaymentItem(
    open val donationType: DonationType,
    open val ownerName: String,
)

data class Paypal(
    override val donationType: DonationType,
    override val ownerName: String,
    val url: String,
): PaymentItem(donationType, ownerName)

enum class DonationType {
    DIEZMO,
    OFRENDA,
}

data class BankAccount(
    override val donationType: DonationType,
    override val ownerName: String,
    val backgroundResource: Int,
    val bankName: String,
    var cardNumber: String,
    var accountNumber: String,
    var clabe: String,
): PaymentItem(donationType, ownerName)
