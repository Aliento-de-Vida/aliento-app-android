package com.alientodevida.alientoapp.domain.entities.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Token(
  @SerialName("access_token")
  var accessToken: String,
  @SerialName("token_type")
  var tokenType: String
)