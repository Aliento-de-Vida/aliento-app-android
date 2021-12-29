package com.alientodevida.alientoapp.data.youtube

import com.alientodevida.alientoapp.data.storage.RoomDao
import com.alientodevida.alientoapp.domain.entities.local.YoutubeVideo
import com.alientodevida.alientoapp.domain.entities.network.asDomain
import com.alientodevida.alientoapp.domain.youtube.VideoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VideoRepositoryImpl (
    private val youtubeApi: YoutubeApi,
    private val roomDao: RoomDao
) : VideoRepository {

    companion object {
        const val YOUTUBE_DEVELOPER_KEY = "AIzaSyD3-lHPYrGTHPUEP_ZpdQEPwx2IXKfznj0"
    }

    override suspend fun getYoutubeChannelVideos(
        channelId: String,
        maxResults: Int,
    ): List<YoutubeVideo> {
        val response = youtubeApi.getYoutubeChannelVideos(
            "https://www.googleapis.com/youtube/v3/search?" +
                    "key=$YOUTUBE_DEVELOPER_KEY" +
                    "&channelId=${channelId}" +
                    "&part=snippet" +
                    "&order=date" +
                    "&maxResults=$maxResults"
        )

        val items = response.asDomain()
        withContext(Dispatchers.IO) { roomDao.insertYoutubeVideos(items) }
        return items
    }

    override suspend fun getYoutubePlaylist(
        playListId: String,
        maxResults: Int,
    ): List<YoutubeVideo> {

        val response = youtubeApi.getYoutubePlaylist(
            "https://www.googleapis.com/youtube/v3/playlistItems?" +
                    "key=$YOUTUBE_DEVELOPER_KEY" +
                    "&playlistId=${playListId}" +
                    "&part=snippet" +
                    "&order=date" +
                    "&maxResults=$maxResults"
        )

        val items = response.asDomain()
        withContext(Dispatchers.IO) { roomDao.insertYoutubeVideos(items) }
        return items
    }

    override fun getCachedVideos(): List<YoutubeVideo> {
        return roomDao.getYoutubeVideos()
    }

}