package com.alientodevida.alientoapp.domain.entities.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class CsrfToken (

    @Json(name = "csrf-token")
    var csrfToken: String,

): Serializable {
    companion object {
        const val key = "Csrf Token"
    }
}