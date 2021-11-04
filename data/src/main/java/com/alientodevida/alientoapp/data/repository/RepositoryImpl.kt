package com.alientodevida.alientoapp.data.repository

import androidx.lifecycle.LiveData
import com.alientodevida.alientoapp.data.networking.RetrofitService
import com.alientodevida.alientoapp.data.storage.RoomDao
import com.alientodevida.alientoapp.domain.Repository
import com.alientodevida.alientoapp.domain.entities.local.ImageUrlEntity
import com.alientodevida.alientoapp.domain.entities.local.PodcastEntity
import com.alientodevida.alientoapp.domain.entities.local.YoutubePlaylistItemEntity
import com.alientodevida.alientoapp.domain.entities.network.Token
import com.alientodevida.alientoapp.domain.entities.network.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val retrofitService: RetrofitService,
    private val roomDao: RoomDao
) : Repository {

    override suspend fun refreshYoutubePlaylist(youtubeKey: String, playListId: String):
            List<YoutubePlaylistItemEntity> {

        val response = retrofitService.getYoutubePlaylist(
            "https://www.googleapis.com/youtube/v3/playlistItems?key=${youtubeKey}" +
                    "&playlistId=${playListId}&part=snippet&order=date&maxResults=50"
        )

        val items = response.asDomainModel()
        withContext(Dispatchers.IO) { roomDao.insertAllYoutubePlaylistitems(items) }
        return items
    }

    override fun getCachedYoutubePlaylist(): List<YoutubePlaylistItemEntity> {
        return roomDao.getYoutubePlaylistitems()
    }

    override suspend fun refreshPodcasts(authorization: String, podcastId: String):
            List<PodcastEntity> {
        val response = retrofitService.getPodcast(authorization, podcastId)
        val items = response.asDomainModel()
        withContext(Dispatchers.IO) { roomDao.insertAllPodcasts(items) }
        return items
    }

    override fun getCachedPodcasts(): LiveData<List<PodcastEntity>> {
        return roomDao.getPodcasts()
    }

    override suspend fun refreshImageUrl(
        authorization: String,
        folderName: String
    ): ImageUrlEntity {
        val searchUrl =
            "https://api.cloudinary.com/api/v1_1/dpeeqsw78/resources/search/?expression=folder=${folderName}"
        val response = retrofitService.getImageUrl(searchUrl, authorization)

        val result = response.asDomainModel(searchUrl)
        withContext(Dispatchers.IO) { roomDao.insertImageUrl(result) }
        return result
    }

    override fun getCachedImageUrl(folderName: String): ImageUrlEntity? {
        val url =
            "https://api.cloudinary.com/api/v1_1/dpeeqsw78/resources/search/?expression=folder=${folderName}"
        return roomDao.getImageUrl(url)
    }

    override fun getCachedImageUrlLiveData(folderName: String): LiveData<ImageUrlEntity?> {
        val url =
            "https://api.cloudinary.com/api/v1_1/dpeeqsw78/resources/search/?expression=folder=${folderName}"
        return roomDao.getImageUrlLiveData(url)
    }

    override suspend fun getToken(
        authorization: String,
        grantType: String
    ): Token {
        return retrofitService.getToken(
            "https://accounts.spotify.com/api/token/",
            authorization,
            grantType
        )
    }

    /*override suspend fun getPlaylist(authorization: String, playlistId: String): PlayList {
        return retrofitService.getPlaylist(authorization, playlistId)
    }*/
}