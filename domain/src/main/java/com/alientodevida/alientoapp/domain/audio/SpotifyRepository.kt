package com.alientodevida.alientoapp.domain.audio

interface SpotifyRepository {
  
  suspend fun refreshPodcasts(podcastId: String): List<Audio>
  suspend fun refreshAudios(artistId: String): List<Audio>
  fun getCachedPodcasts(): List<Audio>
  
}