package com.alientodevida.alientoapp.domain.spotify

import com.alientodevida.alientoapp.domain.entities.local.Audio

interface SpotifyRepository {
  
  suspend fun refreshPodcasts(podcastId: String): List<Audio>
  suspend fun refreshAudios(artistId: String): List<Audio>
  fun getCachedPodcasts(): List<Audio>
  
}