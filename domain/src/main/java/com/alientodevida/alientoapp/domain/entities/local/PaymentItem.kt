package com.alientodevida.alientoapp.domain.entities.local

data class PaymentItem(
  val id: Int,
  val donationType: DonationType,
  val ownerName: String,
  val paypal: Paypal?,
  val bankAccount: BankAccount?,
)

enum class DonationType {
  DIEZMO,
  OFRENDA,
}

data class Paypal(val url: String)

data class BankAccount(
  val backgroundResource: Int,
  val bankName: String,
  var cardNumber: String,
  var accountNumber: String,
  var clabe: String,
)
