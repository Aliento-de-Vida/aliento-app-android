package com.alientodevida.alientoapp.data.entities.network.base

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class ApiError (

    @Json(name = "message")
    var message: String,

): Serializable