package com.alientodevida.alientoapp.data.domain

import androidx.lifecycle.LiveData
import com.alientodevida.alientoapp.data.entities.local.PodcastEntity
import com.alientodevida.alientoapp.data.entities.local.YoutubePlaylistItemEntity
import com.alientodevida.alientoapp.data.entities.network.ImageUrlResponse
import com.alientodevida.alientoapp.data.entities.network.PlayList
import com.alientodevida.alientoapp.data.entities.network.Token

interface Repository {
    suspend fun getImageUrl(authorization: String, folderName: String): ImageUrlResponse

    suspend fun getToken(authorization: String, grantType: String): Token

    suspend fun getPlaylist(authorization: String, playlistId: String): PlayList



    suspend fun refreshPodcasts(authorization: String, podcastId: String)
    fun getPodcasts(): LiveData<List<PodcastEntity>>

    suspend fun refreshYoutubePlaylist(playListId: String, youtubeKey: String)
    fun getYoutubePlaylist(): LiveData<List<YoutubePlaylistItemEntity>>
}