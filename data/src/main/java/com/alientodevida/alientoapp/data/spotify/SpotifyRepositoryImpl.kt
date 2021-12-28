package com.alientodevida.alientoapp.data.spotify

import com.alientodevida.alientoapp.data.storage.RoomDao
import com.alientodevida.alientoapp.domain.entities.local.PodcastEntity
import com.alientodevida.alientoapp.domain.entities.network.asDomain
import com.alientodevida.alientoapp.domain.spotify.SpotifyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SpotifyRepositoryImpl (
    private val spotifyApi: SpotifyApi,
    private val roomDao: RoomDao
) : SpotifyRepository {

    override suspend fun refreshPodcasts(podcastId: String):
            List<PodcastEntity> {
        val response = spotifyApi.getPodcast(podcastId)
        val items = response.asDomain()
        withContext(Dispatchers.IO) { roomDao.insertAllPodcasts(items) }
        return items
    }

    override fun getCachedPodcasts(): List<PodcastEntity> {
        return roomDao.getPodcasts()
    }

    /*override suspend fun getPlaylist(authorization: String, playlistId: String): PlayList {
        return retrofitService.getPlaylist(authorization, playlistId)
    }*/
}