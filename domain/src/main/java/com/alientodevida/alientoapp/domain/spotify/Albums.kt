package com.alientodevida.alientoapp.domain.spotify

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Albums(
  @Serializable
  val href: String? = null,
  @Serializable
  val items: List<Album>
)

@Serializable
data class Album(
  val uri: String,
  val id: String,
  val name: String,
  @SerialName("release_date")
  val releaseDate: String,
  @SerialName("duration_ms")
  val duration: Int = 0,
  val images: List<SpotifyImage>? = null
)