package com.alientodevida.alientoapp.sermons.data.spotify

import com.alientodevida.alientoapp.sermons.data.storage.RoomDao
import com.alientodevida.alientoapp.sermons.domain.audio.Album
import com.alientodevida.alientoapp.sermons.domain.audio.Audio
import com.alientodevida.alientoapp.sermons.domain.audio.SpotifyRepository
import com.alientodevida.alientoapp.sermons.domain.audio.asDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SpotifyRepositoryImpl(
    private val spotifyApi: SpotifyApi,
    private val roomDao: RoomDao
) : SpotifyRepository {

    override suspend fun refreshPodcasts(podcastId: String):
        List<Audio> {
        val response = spotifyApi.getPodcast(podcastId)
        val items = response.asDomain()
        withContext(Dispatchers.IO) { roomDao.insertAudios(items) }
        return items
    }

    override suspend fun refreshAudios(artistId: String): List<Audio> {
        val albums = spotifyApi.getAlbums(artistId).items
        if (albums.isEmpty()) return listOf()

        val tracks: MutableList<Audio> = mutableListOf()
        albums.forEach { album ->
            tracks += getTracks(album).map {
                it.copy(imageUrl = album.images?.firstOrNull()?.url ?: "")
            }
        }

        withContext(Dispatchers.IO) { roomDao.insertAudios(tracks) }

        return tracks
    }

    private suspend fun getTracks(album: Album) = spotifyApi.getTracks(album.id).asDomain(album)

    override suspend fun getCachedPodcasts(): List<Audio> {
        return withContext(Dispatchers.IO) { roomDao.getAudios() }
    }
}