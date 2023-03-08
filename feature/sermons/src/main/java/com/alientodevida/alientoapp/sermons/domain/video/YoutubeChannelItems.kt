package com.alientodevida.alientoapp.sermons.domain.video

import com.alientodevida.alientoapp.domain.video.YoutubeVideo
import kotlinx.serialization.Serializable

@Serializable
data class YoutubeChannelItems(
  var items: List<ChannelItem>
)

@Serializable
data class ChannelItem(
    val id: VideoId,
    val snippet: Snippet
)

@Serializable
data class VideoId(val videoId: String? = null)

/**
 * Convert Network results to domain objects
 */
fun YoutubeChannelItems.asDomain(): List<YoutubeVideo> {
  return items.filter { it.id.videoId != null }.map {
    YoutubeVideo(
      it.snippet.title,
      it.id.videoId!!,
      it.snippet.description,
      it.snippet.publishedAt,
      it.snippet.thumbnails.high?.url
    )
  }
}