package com.alientodevida.alientoapp.data.entities.network

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PlayList(

    @SerializedName("id")
    val id: String? = null,

    @SerializedName("tracks")
    val tracks: PlayListTrack? = null
)

@JsonClass(generateAdapter = true)
data class PlayListTrack(
    @SerializedName("items")
    val items: List<Track>? = null
)

@JsonClass(generateAdapter = true)
data class Track(
    @SerializedName("track")
    val track: TrackUri? = null
)

@JsonClass(generateAdapter = true)
data class TrackUri(
    @SerializedName("album")
    val album: Album? = null,

    @SerializedName("uri")
    val uri: String? = null
)

@JsonClass(generateAdapter = true)
data class Album(
    @SerializedName("images")
    val images: List<AlbumImage>? = null
)

