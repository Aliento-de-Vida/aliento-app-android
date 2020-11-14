package com.alientodevida.alientoapp.data.entities

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class Podcast(
    @SerializedName("href")
    val href: String? = null,

    @SerializedName("items")
    val items: ArrayList<Podcasts>? = null
)

@JsonClass(generateAdapter = true)
data class Podcasts(
    @SerializedName("uri")
    val uri: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("release_date")
    val releaseDate: String? = null,

    @SerializedName("duration_ms")
    val duration: Int = 0,

    @SerializedName("images")
    val images: ArrayList<AlbumImage>? = null
)

@JsonClass(generateAdapter = true)
data class AlbumImage(
    @SerializedName("url")
    val url: String? = null
)