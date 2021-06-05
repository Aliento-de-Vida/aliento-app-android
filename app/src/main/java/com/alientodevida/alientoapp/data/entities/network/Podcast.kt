package com.alientodevida.alientoapp.data.entities.network

import com.alientodevida.alientoapp.data.entities.local.PodcastEntity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Podcast(
    @Json(name = "href")
    val href: String? = null,

    @Json(name = "items")
    val items: List<Podcasts>
)

@JsonClass(generateAdapter = true)
data class Podcasts(
    @Json(name = "uri")
    val uri: String,

    @Json(name = "name")
    val name: String,

    @Json(name = "release_date")
    val releaseDate: String,

    @Json(name = "duration_ms")
    val duration: Int = 0,

    @Json(name = "images")
    val images: List<AlbumImage>? = null
)

@JsonClass(generateAdapter = true)
data class AlbumImage(
    @Json(name = "url")
    val url: String? = null
)

/**
 * Convert Network results to domain objects
 */
fun Podcast.asDomainModel(): List<PodcastEntity> {
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