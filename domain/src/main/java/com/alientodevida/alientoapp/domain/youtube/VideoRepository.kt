package com.alientodevida.alientoapp.domain.youtube

import com.alientodevida.alientoapp.domain.entities.local.YoutubeVideo

interface VideoRepository {

    suspend fun getYoutubeChannelVideos(
        channelId: String,
        maxResults: Int = 50,
    ): List<YoutubeVideo>
    suspend fun getYoutubePlaylist(
        playListId: String,
        maxResults: Int = 50,
    ): List<YoutubeVideo>
    fun getCachedVideos(): List<YoutubeVideo>

}