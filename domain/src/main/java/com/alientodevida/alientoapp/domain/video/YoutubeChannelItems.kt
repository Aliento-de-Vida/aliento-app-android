package com.alientodevida.alientoapp.domain.video

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
data class VideoId(val videoId: String)

/**
 * Convert Network results to domain objects
 */
fun YoutubeChannelItems.asDomain(): List<YoutubeVideo> {
  return items.map {
    YoutubeVideo(
      it.snippet.title,
      it.id.videoId,
      it.snippet.description,
      it.snippet.publishedAt,
      it.snippet.thumbnails.high?.url
    )
  }
}