package com.alientodevida.alientoapp.data.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class Token (

    @Json(name = "access_token")
    var accessToken: String,

    @Json(name = "token_type")
    var tokenType: String

): Serializable {
    companion object {
        const val key = "Token"
    }
}