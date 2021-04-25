package com.alientodevida.alientoapp.domain

import androidx.lifecycle.LiveData
import com.alientodevida.alientoapp.domain.entities.local.ImageUrlEntity
import com.alientodevida.alientoapp.domain.entities.local.PodcastEntity
import com.alientodevida.alientoapp.domain.entities.local.YoutubePlaylistItemEntity
import com.alientodevida.alientoapp.domain.entities.network.AskPrayerResponse
import com.alientodevida.alientoapp.domain.entities.network.CsrfToken
import com.alientodevida.alientoapp.domain.entities.network.Token
import com.alientodevida.alientoapp.domain.entities.network.Transmision
import com.alientodevida.alientoapp.domain.entities.network.base.ApiError
import com.alientodevida.alientoapp.domain.entities.network.base.ApiResult

interface Repository {

    // TODO: 14/03/21 need to add error handling in retrofit

    suspend fun refreshYoutubePlaylist(youtubeKey: String, playListId: String):
            ApiResult<List<YoutubePlaylistItemEntity>, ApiError>
    fun getCachedYoutubePlaylist(): List<YoutubePlaylistItemEntity>

    suspend fun refreshPodcasts(authorization: String, podcastId: String):
            ApiResult<List<PodcastEntity>, ApiError>
    fun getCachedPodcasts(): LiveData<List<PodcastEntity>>

    suspend fun refreshImageUrl(authorization: String, folderName: String):
            ApiResult<ImageUrlEntity, ApiError>
    fun getCachedImageUrl(folderName: String): ImageUrlEntity?
    fun getCachedImageUrlLiveData(folderName: String): LiveData<ImageUrlEntity?>

    suspend fun getToken(authorization: String, grantType: String): ApiResult<Token, ApiError>

    suspend fun getCsrfToken(): ApiResult<CsrfToken, ApiError>

    suspend fun getTransmision(): ApiResult<Transmision, ApiError>

    suspend fun sendPrayerRequest(
        csrfToken: String,
        asunto: String,
        nombre: String,
        email: String,
        whatsapp: String,
        mensaje: String
    ): ApiResult<AskPrayerResponse, ApiError>

    //suspend fun getPlaylist(authorization: String, playlistId: String): PlayList
}