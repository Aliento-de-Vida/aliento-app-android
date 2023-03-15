package com.alientodevida.alientoapp.domain.video

interface VideoRepository {

    suspend fun getYoutubeChannelVideos(
        channelId: String,
        maxResults: Int = 50,
    ): List<YoutubeVideo>

    suspend fun getYoutubePlaylist(
        playListId: String,
        maxResults: Int = 50,
    ): List<YoutubeVideo>

    suspend fun getCachedVideos(): List<YoutubeVideo>
}
