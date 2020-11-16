package com.alientodevida.alientoapp.data.domain

import com.alientodevida.alientoapp.data.entities.*

interface Repository {
    suspend fun getImageUrl(authorization: String, folderName: String): ImageUrlResponse
    suspend fun getToken(authorization: String, grantType: String): Token
    suspend fun getPlaylist(authorization: String, playlistId: String): PlayList
    suspend fun getPodcast(authorization: String, podcastId: String): Podcast
    suspend fun getYoutubePlayList(playListId: String, youtubeKey: String): PlayListItems
}