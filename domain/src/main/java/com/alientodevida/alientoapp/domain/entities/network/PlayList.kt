package com.alientodevida.alientoapp.domain.entities.network

import kotlinx.serialization.Serializable

@Serializable
data class PlayList(
    val id: String? = null,
    val tracks: PlayListTrack? = null
)

@Serializable
data class PlayListTrack(
    val items: List<Track>? = null
)

@Serializable
data class Track(
    val track: TrackUri? = null
)

@Serializable
data class TrackUri(
    val album: Album? = null,
    val uri: String? = null
)

@Serializable
data class Album(
    val images: List<AlbumImage>? = null
)

