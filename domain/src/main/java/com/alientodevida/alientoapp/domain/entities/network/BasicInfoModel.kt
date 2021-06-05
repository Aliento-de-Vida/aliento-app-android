package com.alientodevida.alientoapp.domain.entities.network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BasicInfoModel(
    var image: String? = null,
    var video: String? = null,
    var text: String? = null
)