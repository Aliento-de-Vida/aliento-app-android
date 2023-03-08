package com.alientodevida.alientoapp.sermons.domain.audio

interface SpotifyRepository {
  
  suspend fun refreshPodcasts(podcastId: String): List<Audio>
  suspend fun refreshAudios(artistId: String): List<Audio>
  suspend fun getCachedPodcasts(): List<Audio>
  
}