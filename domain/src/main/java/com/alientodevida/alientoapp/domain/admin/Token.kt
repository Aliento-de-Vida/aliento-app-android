package com.alientodevida.alientoapp.domain.admin

import kotlinx.serialization.Serializable

@Serializable
data class Token(
  val jwt: String,
)
