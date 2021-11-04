package com.alientodevida.alientoapp.domain

import androidx.lifecycle.LiveData
import com.alientodevida.alientoapp.domain.entities.local.ImageUrlEntity
import com.alientodevida.alientoapp.domain.entities.local.PodcastEntity
import com.alientodevida.alientoapp.domain.entities.local.YoutubePlaylistItemEntity
import com.alientodevida.alientoapp.domain.entities.network.Token

interface Repository {

    // TODO: 14/03/21 need to add error handling in retrofit

    suspend fun refreshYoutubePlaylist(youtubeKey: String, playListId: String):
            List<YoutubePlaylistItemEntity>
    fun getCachedYoutubePlaylist(): List<YoutubePlaylistItemEntity>

    suspend fun refreshPodcasts(authorization: String, podcastId: String):
            List<PodcastEntity>
    fun getCachedPodcasts(): LiveData<List<PodcastEntity>>

    suspend fun refreshImageUrl(authorization: String, folderName: String): ImageUrlEntity
    fun getCachedImageUrl(folderName: String): ImageUrlEntity?
    fun getCachedImageUrlLiveData(folderName: String): LiveData<ImageUrlEntity?>

    suspend fun getToken(authorization: String, grantType: String): Token

    //suspend fun getPlaylist(authorization: String, playlistId: String): PlayList
}