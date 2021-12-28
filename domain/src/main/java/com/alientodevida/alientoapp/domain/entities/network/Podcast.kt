package com.alientodevida.alientoapp.domain.entities.network

import com.alientodevida.alientoapp.domain.entities.local.PodcastEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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

/**
 * Convert Network results to domain objects
 */
fun Podcast.asDomain(): List<PodcastEntity> {
    return items.map {
        PodcastEntity(
            it.uri,
            it.releaseDate,
            it.name,
            it.duration,
            it.images?.first()?.url.toString()
        )
    }
}