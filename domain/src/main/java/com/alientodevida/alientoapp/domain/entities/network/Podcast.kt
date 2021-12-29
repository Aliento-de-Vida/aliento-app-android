package com.alientodevida.alientoapp.domain.entities.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import com.alientodevida.alientoapp.domain.entities.local.Podcast as DomainPodcast

@Serializable
data class Podcast(
    @Serializable
    val href: String? = null,
    @Serializable
    val items: List<Podcasts>
)

@Serializable
data class Podcasts(
    val uri: String,
    val name: String,
    @SerialName("release_date")
    val releaseDate: String,
    @SerialName("duration_ms")
    val duration: Int = 0,
    val images: List<AlbumImage>? = null
)

@Serializable
data class AlbumImage(
    val url: String? = null
)

fun Podcast.asDomain(): List<DomainPodcast> {
    return items.map {
        DomainPodcast(
            it.uri,
            it.releaseDate,
            it.name,
            it.duration,
            it.images?.first()?.url.toString()
        )
    }
}