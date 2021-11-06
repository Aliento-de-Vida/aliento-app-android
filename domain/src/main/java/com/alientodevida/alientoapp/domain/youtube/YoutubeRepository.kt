package com.alientodevida.alientoapp.domain.youtube

import com.alientodevida.alientoapp.domain.entities.local.YoutubePlaylistItemEntity

interface YoutubeRepository {

    suspend fun refreshYoutubePlaylist(playListId: String):
            List<YoutubePlaylistItemEntity>
    fun getCachedYoutubePlaylist(): List<YoutubePlaylistItemEntity>

    //suspend fun getPlaylist(authorization: String, playlistId: String): PlayList
}