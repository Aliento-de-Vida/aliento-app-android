package com.alientodevida.alientoapp.domain.entities.network

import kotlinx.serialization.Serializable

@Serializable
data class AskPrayerResponse(
  var status: String,
  var response: String,
)