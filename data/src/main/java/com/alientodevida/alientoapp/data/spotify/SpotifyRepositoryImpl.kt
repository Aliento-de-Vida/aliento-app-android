package com.alientodevida.alientoapp.data.spotify

import com.alientodevida.alientoapp.data.storage.RoomDao
import com.alientodevida.alientoapp.domain.entities.local.Podcast
import com.alientodevida.alientoapp.domain.entities.network.asDomain
import com.alientodevida.alientoapp.domain.spotify.SpotifyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SpotifyRepositoryImpl (
    private val spotifyApi: SpotifyApi,
    private val roomDao: RoomDao
) : SpotifyRepository {

    override suspend fun refreshPodcasts(podcastId: String):
            List<Podcast> {
        val response = spotifyApi.getPodcast(podcastId)
        val items = response.asDomain()
        withContext(Dispatchers.IO) { roomDao.insertPodcasts(items) }
        return items
    }

    override fun getCachedPodcasts(): List<Podcast> {
        return roomDao.getPodcasts()
    }

}