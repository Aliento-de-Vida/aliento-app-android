package com.alientodevida.alientoapp.data.entities

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImageUrlResponse(
    val resources: List<Resources>
)

@JsonClass(generateAdapter = true)
data class Resources(
    val url: String
)