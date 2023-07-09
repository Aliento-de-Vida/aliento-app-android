package com.alientodevida.alientoapp.sermons.domain.video

import com.alientodevida.alientoapp.domain.video.YoutubeVideo
import kotlinx.serialization.Serializable

@Serializable
data class YoutubePlaylistItems(
    var items: List<PlaylistItem>,
)

@Serializable
data class PlaylistItem(val snippet: Snippet)

@Serializable
class Snippet(
    val publishedAt: String,
    val title: String,
    val description: String,
    val thumbnails: Thumbnails,
    val resourceId: ResourceId? = null,
)

@Serializable
data class ResourceId(
    val kind: String,
    val videoId: String,
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
fun YoutubePlaylistItems.asDomain(): List<YoutubeVideo> {
    return items.filter { it.snippet.thumbnails.high != null }.map {
        YoutubeVideo(
            it.snippet.title,
            it.snippet.resourceId!!.videoId,
            it.snippet.description,
            it.snippet.publishedAt,
            it.snippet.thumbnails.high!!.url,
        )
    }
}
