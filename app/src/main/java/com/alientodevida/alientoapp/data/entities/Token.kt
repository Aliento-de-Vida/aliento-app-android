package com.alientodevida.alientoapp.data.entities

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class Token (

    @SerializedName("access_token")
    var access_token: String? = null,

    @SerializedName("token_type")
    var token_type: String? = null

): Serializable