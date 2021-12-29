package com.alientodevida.alientoapp.domain.spotify

import com.alientodevida.alientoapp.domain.entities.local.Podcast

interface SpotifyRepository {

    suspend fun refreshPodcasts(podcastId: String): List<Podcast>
    fun getCachedPodcasts(): List<Podcast>

}