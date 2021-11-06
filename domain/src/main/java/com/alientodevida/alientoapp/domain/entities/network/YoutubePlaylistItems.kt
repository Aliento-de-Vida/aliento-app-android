package com.alientodevida.alientoapp.domain.entities.network

import com.alientodevida.alientoapp.domain.entities.local.YoutubePlaylistItemEntity
import kotlinx.serialization.Serializable

@Serializable
data class YoutubePlaylistItems(
    var items: List<Playlistitem>
)

@Serializable
data class Playlistitem(
    val snippet: Snippet
)

@Serializable
class Snippet(
    val publishedAt: String,
    val title: String,
    val description: String,
    val thumbnails: Thumbnails,
    val resourceId: ResourceId
)

@Serializable
data class ResourceId(
    val kind: String,
    val videoId: String
)

@Serializable
data class Thumbnails(
    val default: Default? = null,
    val medium: Medium? = null,
    var high: High? = null,
)

@Serializable
data class Default(
    val url: String,
    val width: Int,
    val height: Int,
)

@Serializable
data class Medium(
    val url: String,
    val width: Int,
    val height: Int,
)

@Serializable
data class High(
    val url: String,
    val width: Int,
    val height: Int,
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