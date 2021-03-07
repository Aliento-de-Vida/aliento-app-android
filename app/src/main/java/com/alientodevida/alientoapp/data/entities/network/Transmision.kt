package com.alientodevida.alientoapp.data.entities.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class Transmision (

    @Json(name = "video_incrustado")
    var video: String,

    @Json(name = "fecha_publicacion")
    var fechaPublicacion: String,

): Serializable {
    companion object {
        const val key = "Transmision"
    }
}