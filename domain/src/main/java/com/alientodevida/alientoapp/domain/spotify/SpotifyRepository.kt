package com.alientodevida.alientoapp.domain.spotify

import com.alientodevida.alientoapp.domain.entities.local.PodcastEntity

interface SpotifyRepository {

    suspend fun refreshPodcasts(podcastId: String): List<PodcastEntity>
    fun getCachedPodcasts(): List<PodcastEntity>

}