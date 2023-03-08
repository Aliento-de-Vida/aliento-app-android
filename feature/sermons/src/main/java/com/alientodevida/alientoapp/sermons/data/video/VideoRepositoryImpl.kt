package com.alientodevida.alientoapp.sermons.data.video

import com.alientodevida.alientoapp.domain.video.VideoRepository
import com.alientodevida.alientoapp.domain.video.YoutubeVideo
import com.alientodevida.alientoapp.sermons.data.storage.RoomDao
import com.alientodevida.alientoapp.sermons.domain.video.asDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class VideoRepositoryImpl @Inject constructor(
  private val youtubeApi: YoutubeApi,
  private val roomDao: RoomDao,
  @Named("youtube-key")
  private val youtubeKey: String,
) : VideoRepository {

  override suspend fun getYoutubeChannelVideos(
    channelId: String,
    maxResults: Int,
  ): List<YoutubeVideo> {
    val response = youtubeApi.getYoutubeChannelVideos(
      "https://www.googleapis.com/youtube/v3/search?" +
          "key=$youtubeKey" +
          "&channelId=${channelId}" +
          "&part=snippet" +
          "&order=date" +
          "&maxResults=$maxResults"
    )
    
    val items = response.asDomain()
    withContext(Dispatchers.IO) { roomDao.insertVideos(items) }
    return items
  }
  
  override suspend fun getYoutubePlaylist(
    playListId: String,
    maxResults: Int,
  ): List<YoutubeVideo> {
    
    val response = youtubeApi.getYoutubePlaylist(
      "https://www.googleapis.com/youtube/v3/playlistItems?" +
          "key=$youtubeKey" +
          "&playlistId=${playListId}" +
          "&part=snippet" +
          "&order=date" +
          "&maxResults=$maxResults"
    )
    
    val items = response.asDomain()
    withContext(Dispatchers.IO) { roomDao.insertVideos(items) }
    return items
  }
  
  override suspend fun getCachedVideos(): List<YoutubeVideo> =
    withContext(Dispatchers.IO) { roomDao.getVideos() }
  
}