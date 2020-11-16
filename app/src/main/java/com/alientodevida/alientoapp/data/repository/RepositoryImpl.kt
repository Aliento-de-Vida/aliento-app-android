package com.alientodevida.alientoapp.data.repository

import androidx.lifecycle.LiveData
import com.alientodevida.alientoapp.data.domain.Repository
import com.alientodevida.alientoapp.data.entities.network.ImageUrlResponse
import com.alientodevida.alientoapp.data.entities.network.PlayList
import com.alientodevida.alientoapp.data.entities.network.Token
import com.alientodevida.alientoapp.data.entities.network.asDomainModel
import com.alientodevida.alientoapp.data.entities.local.PodcastEntity
import com.alientodevida.alientoapp.data.entities.local.YoutubePlaylistItemEntity
import com.alientodevida.alientoapp.data.networking.RetrofitService
import com.alientodevida.alientoapp.data.storage.RoomDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val retrofitService: RetrofitService,
    private val roomDao: RoomDao
): Repository {

    override suspend fun refreshYoutubePlaylist(youtubeKey: String, playListId: String) {
        withContext(Dispatchers.IO) {
            val playList = retrofitService.getYoutubePlaylist(
                "https://www.googleapis.com/youtube/v3/playlistItems?key=${youtubeKey}" +
                        "&playlistId=${playListId}&part=snippet&order=date&maxResults=50"
            )
            roomDao.insertAllYoutubePlaylistitems(playList.asDomainModel())
        }
    }
    override fun getYoutubePlaylist(): LiveData<List<YoutubePlaylistItemEntity>> {
        return roomDao.getYoutubePlaylistitems()
    }

    override suspend fun refreshPodcasts(authorization: String, podcastId: String) {
        withContext(Dispatchers.IO) {
            val items = retrofitService.getPodcast(authorization, podcastId)
            roomDao.insertAllPodcasts(items.asDomainModel())
        }
    }
    override fun getPodcasts(): LiveData<List<PodcastEntity>> {
        return roomDao.getPodcasts()
    }



    override suspend fun getImageUrl(authorization: String, folderName: String): ImageUrlResponse {
        return retrofitService.getImageUrl(
            "https://api.cloudinary.com/api/v1_1/dpeeqsw78/resources/search/?expression=folder=${folderName}",
            authorization
        )
    }

    override suspend fun getToken(authorization: String, grantType: String): Token {
        return retrofitService.getToken(
            "https://accounts.spotify.com/api/token/",
            authorization,
            grantType
        )
    }

    override suspend fun getPlaylist(authorization: String, playlistId: String): PlayList {
        return retrofitService.getPlaylist(authorization, playlistId)
    }
}