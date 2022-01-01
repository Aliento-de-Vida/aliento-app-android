package com.alientodevida.alientoapp.domain.spotify

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import com.alientodevida.alientoapp.domain.entities.local.Audio as DomainPodcast

@Serializable
data class Podcasts(
  @Serializable
  val href: String? = null,
  @Serializable
  val items: List<Podcast>
)

@Serializable
data class Podcast(
  val uri: String,
  val name: String,
  @SerialName("release_date")
  val releaseDate: String,
  @SerialName("duration_ms")
  val duration: Int = 0,
  val images: List<SpotifyImage>? = null
)

@Serializable
data class SpotifyImage(
  val url: String? = null
)

fun Podcasts.asDomain(): List<DomainPodcast> {
  return items.map { it.asDomain() }
}

fun Podcast.asDomain() = DomainPodcast(
  this.uri,
  this.releaseDate,
  this.name,
  this.releaseDate,
  this.duration,
  this.images?.first()?.url.toString()
)