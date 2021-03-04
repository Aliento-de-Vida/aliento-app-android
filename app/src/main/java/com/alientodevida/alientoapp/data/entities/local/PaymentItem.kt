package com.alientodevida.alientoapp.data.entities.local


sealed class PaymentItem(
    open val donationType: DonationType,
    open val ownerName: String,
    open val cardColor: String,
)

data class Paypal(
    override val donationType: DonationType,
    override val ownerName: String,
    override val cardColor: String,
    val url: String,
): PaymentItem(donationType, ownerName, cardColor)

enum class DonationType {
    DIEZMO,
    OFRENDA,
}

data class BankAccount(
    override val donationType: DonationType,
    override val ownerName: String,
    override val cardColor: String,
    val bankName: String,
    var cardNumber: String,
    var accountNumber: String,
    var clabe: String,
): PaymentItem(donationType, ownerName, cardColor)
