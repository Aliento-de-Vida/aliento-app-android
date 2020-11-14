package com.alientodevida.alientoapp.data.domain

import com.alientodevida.alientoapp.data.entities.ImageUrlResponse
import com.alientodevida.alientoapp.data.entities.PlayList
import com.alientodevida.alientoapp.data.entities.Podcast
import com.alientodevida.alientoapp.data.entities.Token

interface Repository {
    suspend fun getImageUrl(authorization: String, folderName: String): ImageUrlResponse
    suspend fun getToken(): Token?
    suspend fun getPlaylist(authorization: String, playlistId: String): PlayList
    suspend fun getPodcast(authorization: String, podcastId: String): Podcast
}