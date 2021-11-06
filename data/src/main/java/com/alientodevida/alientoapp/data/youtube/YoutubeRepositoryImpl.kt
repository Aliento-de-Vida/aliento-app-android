package com.alientodevida.alientoapp.data.youtube

import com.alientodevida.alientoapp.data.storage.RoomDao
import com.alientodevida.alientoapp.domain.entities.local.YoutubePlaylistItemEntity
import com.alientodevida.alientoapp.domain.entities.network.asDomainModel
import com.alientodevida.alientoapp.domain.youtube.YoutubeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class YoutubeRepositoryImpl (
    private val youtubeApi: YoutubeApi,
    private val roomDao: RoomDao
) : YoutubeRepository {

    companion object {
        const val YOUTUBE_DEVELOPER_KEY = "AIzaSyD3-lHPYrGTHPUEP_ZpdQEPwx2IXKfznj0"
    }

    override suspend fun refreshYoutubePlaylist(playListId: String):
            List<YoutubePlaylistItemEntity> {

        val response = youtubeApi.getYoutubePlaylist(
            "https://www.googleapis.com/youtube/v3/playlistItems?key=$YOUTUBE_DEVELOPER_KEY" +
                    "&playlistId=${playListId}&part=snippet&order=date&maxResults=50"
        )

        val items = response.asDomainModel()
        withContext(Dispatchers.IO) { roomDao.insertAllYoutubePlaylistitems(items) }
        return items
    }

    override fun getCachedYoutubePlaylist(): List<YoutubePlaylistItemEntity> {
        return roomDao.getYoutubePlaylistitems()
    }

    /*override suspend fun getPlaylist(authorization: String, playlistId: String): PlayList {
        return retrofitService.getPlaylist(authorization, playlistId)
    }*/
}