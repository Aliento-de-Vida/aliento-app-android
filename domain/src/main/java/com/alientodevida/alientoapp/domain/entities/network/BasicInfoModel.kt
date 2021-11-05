package com.alientodevida.alientoapp.domain.entities.network

import kotlinx.serialization.Serializable

@Serializable
data class BasicInfoModel(
    var image: String? = null,
    var video: String? = null,
    var text: String? = null
)