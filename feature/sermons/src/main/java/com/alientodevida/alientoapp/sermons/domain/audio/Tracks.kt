package com.alientodevida.alientoapp.sermons.domain.audio

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Tracks(
  @Serializable
  val href: String? = null,
  @Serializable
  val items: List<Track>
)

@Serializable
data class Track(
  val uri: String,
  val name: String,
  @SerialName("duration_ms")
  val duration: Int = 0,
  val images: List<SpotifyImage>? = null
)

fun Tracks.asDomain(album: Album) =
  items.map { it.asDomain(album) }

fun Track.asDomain(album: Album) = Audio(
  this.uri,
  album.releaseDate,
  this.name,
  album.name,
  this.duration,
  this.images?.first()?.url.toString()
)