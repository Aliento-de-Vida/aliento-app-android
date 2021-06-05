package com.alientodevida.alientoapp.domain.entities.network

import com.alientodevida.alientoapp.domain.entities.local.YoutubePlaylistItemEntity
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class YoutubePlaylistItems(
    var items: List<Playlistitem>
)

@JsonClass(generateAdapter = true)
data class Playlistitem(
    val snippet: Snippet
)
@JsonClass(generateAdapter = true)
class Snippet(
    val publishedAt: String,
    val title: String,
    val description: String,
    val thumbnails: Thumbnails,
    val resourceId: ResourceId
)

@JsonClass(generateAdapter = true)
data class ResourceId(
    val kind: String,
    val videoId: String
)

@JsonClass(generateAdapter = true)
data class Thumbnails(
    val default: Default?,
    val medium: Medium?,
    var high: High?
)

@JsonClass(generateAdapter = true)
data class Default(
    val url: String,
    val width: String,
    val height: String,
)

@JsonClass(generateAdapter = true)
data class Medium(
    val url: String,
    val width: String,
    val height: String,
)

@JsonClass(generateAdapter = true)
data class High(
    val url: String,
    val width: String,
    val height: String,
)


/**
 * Convert Network results to domain objects
 */
fun YoutubePlaylistItems.asDomainModel(): List<YoutubePlaylistItemEntity> {
    return items.map {
        YoutubePlaylistItemEntity(
            it.snippet.title,
            it.snippet.resourceId.videoId,
            it.snippet.description,
            it.snippet.publishedAt,
            it.snippet.thumbnails.high?.url
        )
    }
}