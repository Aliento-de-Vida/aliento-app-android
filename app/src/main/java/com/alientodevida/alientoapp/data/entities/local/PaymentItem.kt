package com.alientodevida.alientoapp.data.entities.local

import javax.annotation.Resource


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
    @Resource
    val gradient: Int,
    val bankName: String,
    var cardNumber: String,
    var accountNumber: String,
    var clabe: String,
    @Resource
    var bankLogo: Int,
    @Resource
    var cardLogo: Int,
): PaymentItem(donationType, ownerName)
