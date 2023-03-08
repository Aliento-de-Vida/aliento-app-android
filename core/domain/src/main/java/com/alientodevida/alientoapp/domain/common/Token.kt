package com.alientodevida.alientoapp.domain.common

import kotlinx.serialization.Serializable

@Serializable
data class Token(
  val jwt: String,
)
