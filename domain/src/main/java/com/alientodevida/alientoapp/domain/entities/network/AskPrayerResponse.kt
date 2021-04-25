package com.alientodevida.alientoapp.domain.entities.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class AskPrayerResponse (

    @Json(name = "estatus")
    var status: String,

    @Json(name = "respuesta")
    var response: String,

): Serializable {
    companion object {
        const val key = "AskPrayerResponse"
    }
}