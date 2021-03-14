package com.alientodevida.alientoapp.data.domain

import androidx.lifecycle.LiveData
import com.alientodevida.alientoapp.data.entities.local.ImageUrlEntity
import com.alientodevida.alientoapp.data.entities.local.PodcastEntity
import com.alientodevida.alientoapp.data.entities.local.YoutubePlaylistItemEntity
import com.alientodevida.alientoapp.data.entities.network.CsrfToken
import com.alientodevida.alientoapp.data.entities.network.Token
import com.alientodevida.alientoapp.data.entities.network.Transmision

interface Repository {

    suspend fun refreshYoutubePlaylist(youtubeKey: String, playListId: String): List<YoutubePlaylistItemEntity>
    fun getYoutubePlaylist(): List<YoutubePlaylistItemEntity>

    suspend fun refreshPodcasts(authorization: String, podcastId: String)
    fun getPodcasts(): LiveData<List<PodcastEntity>>

    suspend fun refreshImageUrl(authorization: String, folderName: String): ImageUrlEntity
    fun getImageUrl(folderName: String): ImageUrlEntity?
    fun getImageUrlLiveData(folderName: String): LiveData<ImageUrlEntity?>

    suspend fun getToken(authorization: String, grantType: String): Token

    suspend fun getCsrfToken(): CsrfToken

    suspend fun getTransmision(): Transmision

    //suspend fun getPlaylist(authorization: String, playlistId: String): PlayList
}