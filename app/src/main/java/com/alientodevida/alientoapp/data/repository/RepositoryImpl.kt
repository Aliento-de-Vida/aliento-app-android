package com.alientodevida.alientoapp.data.repository

import androidx.lifecycle.LiveData
import com.alientodevida.alientoapp.data.domain.Repository
import com.alientodevida.alientoapp.data.entities.local.ImageUrlEntity
import com.alientodevida.alientoapp.data.entities.local.PodcastEntity
import com.alientodevida.alientoapp.data.entities.local.YoutubePlaylistItemEntity
import com.alientodevida.alientoapp.data.entities.network.*
import com.alientodevida.alientoapp.data.entities.network.base.ApiError
import com.alientodevida.alientoapp.data.entities.network.base.ApiResult
import com.alientodevida.alientoapp.data.networking.RetrofitService
import com.alientodevida.alientoapp.data.storage.RoomDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val retrofitService: RetrofitService,
    private val roomDao: RoomDao
): Repository {

    override suspend fun refreshYoutubePlaylist(youtubeKey: String, playListId: String):
        ApiResult<List<YoutubePlaylistItemEntity>, ApiError> {

        val response = retrofitService.getYoutubePlaylist(
            "https://www.googleapis.com/youtube/v3/playlistItems?key=${youtubeKey}" +
                    "&playlistId=${playListId}&part=snippet&order=date&maxResults=50"
        )

        return when (response) {
            is ApiResult.Success -> {
                val items = response.body.asDomainModel()
                withContext(Dispatchers.IO) {roomDao.insertAllYoutubePlaylistitems(items) }
                return ApiResult.Success(items)
            }
            is ApiResult.Failure -> ApiResult.Failure(response.responseError)
        }
    }
    override fun getCachedYoutubePlaylist(): List<YoutubePlaylistItemEntity> {
        return roomDao.getYoutubePlaylistitems()
    }

    override suspend fun refreshPodcasts(authorization: String, podcastId: String):
        ApiResult<List<PodcastEntity>, ApiError> {

        return when (val response = retrofitService.getPodcast(authorization, podcastId)) {
            is ApiResult.Success -> {
                val items = response.body.asDomainModel()
                withContext(Dispatchers.IO) {roomDao.insertAllPodcasts(items) }
                return ApiResult.Success(items)
            }
            is ApiResult.Failure -> ApiResult.Failure(response.responseError)
        }
    }
    override fun getCachedPodcasts(): LiveData<List<PodcastEntity>> {
        return roomDao.getPodcasts()
    }

    override suspend fun refreshImageUrl(authorization: String, folderName: String): ApiResult<ImageUrlEntity, ApiError> {
        val searchUrl = "https://api.cloudinary.com/api/v1_1/dpeeqsw78/resources/search/?expression=folder=${folderName}"

        return when (val response = retrofitService.getImageUrl(searchUrl, authorization)) {
            is ApiResult.Success -> {
                val result = ApiResult.Success(response.body.asDomainModel(searchUrl))
                withContext(Dispatchers.IO) { roomDao.insertImageUrl(result.body) }
                return result
            }
            is ApiResult.Failure -> ApiResult.Failure(response.responseError)
        }
    }
    override fun getCachedImageUrl(folderName: String): ImageUrlEntity? {
        val url = "https://api.cloudinary.com/api/v1_1/dpeeqsw78/resources/search/?expression=folder=${folderName}"
        return roomDao.getImageUrl(url)
    }
    override fun getCachedImageUrlLiveData(folderName: String): LiveData<ImageUrlEntity?> {
        val url = "https://api.cloudinary.com/api/v1_1/dpeeqsw78/resources/search/?expression=folder=${folderName}"
        return roomDao.getImageUrlLiveData(url)
    }

    override suspend fun getToken(authorization: String, grantType: String): ApiResult<Token, ApiError> {
        return retrofitService.getToken(
            "https://accounts.spotify.com/api/token/",
            authorization,
            grantType
        )
    }

    override suspend fun getCsrfToken(): ApiResult<CsrfToken, ApiError> {
        return retrofitService.getCsrfToken(
                "https://alientodevida.mx/api-adv/get-csrf-token/",
        )
    }
    override suspend fun getTransmision(): ApiResult<Transmision, ApiError> {
        return retrofitService.getTransmision(
                "https://alientodevida.mx/api-adv/get-transmision/",
        )
    }
    override suspend fun sendPrayerRequest(
        csrfToken: String,
        asunto: String,
        nombre: String,
        email: String,
        whatsapp: String,
        mensaje: String
    ): ApiResult<AskPrayerResponse, ApiError> {
        return retrofitService.sendPrayerRequest(
                "https://alientodevida.mx/api-adv/post-mensaje-oracion/",
            csrfToken,
            asunto,
            nombre,
            email,
            whatsapp,
            mensaje,
        )
    }

    /*override suspend fun getPlaylist(authorization: String, playlistId: String): PlayList {
        return retrofitService.getPlaylist(authorization, playlistId)
    }*/
}