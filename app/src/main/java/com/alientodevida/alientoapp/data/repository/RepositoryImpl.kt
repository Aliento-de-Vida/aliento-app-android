package com.alientodevida.alientoapp.data.repository

import com.alientodevida.alientoapp.data.domain.Repository
import com.alientodevida.alientoapp.data.entities.PlayList
import com.alientodevida.alientoapp.data.entities.Podcast
import com.alientodevida.alientoapp.data.entities.Token
import com.alientodevida.alientoapp.data.networking.RetrofitService
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val retrofitService: RetrofitService
): Repository {

    override suspend fun getToken(authorization: String, grantType: String): Token? {
        return retrofitService.getToken(authorization, grantType)
    }

    override suspend fun getPlaylist(authorization: String, playlistId: String): PlayList {
        return retrofitService.getPlaylist(authorization, playlistId)
    }

    override suspend fun getPodcast(authorization: String, podcastId: String): Podcast {
        return retrofitService.getPodcast(authorization, podcastId)
    }
}