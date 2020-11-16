package com.alientodevida.alientoapp.data.domain

import androidx.lifecycle.LiveData
import com.alientodevida.alientoapp.data.entities.local.ImageUrlEntity
import com.alientodevida.alientoapp.data.entities.local.PodcastEntity
import com.alientodevida.alientoapp.data.entities.local.YoutubePlaylistItemEntity
import com.alientodevida.alientoapp.data.entities.network.Token

interface Repository {

    suspend fun refreshYoutubePlaylist(youtubeKey: String, playListId: String)
    fun getYoutubePlaylist(): LiveData<List<YoutubePlaylistItemEntity>>

    suspend fun refreshPodcasts(authorization: String, podcastId: String)
    fun getPodcasts(): LiveData<List<PodcastEntity>>

    suspend fun refreshImageUrl(authorization: String, folderName: String)
    fun getImageUrl(folderName: String): LiveData<ImageUrlEntity>


    suspend fun getToken(authorization: String, grantType: String): Token

    //suspend fun getPlaylist(authorization: String, playlistId: String): PlayList
}