package com.alientodevida.alientoapp.domain.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpotifyToken(
    @SerialName("access_token")
    var accessToken: String,
    @SerialName("token_type")
    var tokenType: String
)