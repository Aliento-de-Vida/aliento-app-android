package com.alientodevida.alientoapp.data.youtube

import com.alientodevida.alientoapp.domain.entities.network.YoutubePlaylistItems
import retrofit2.http.GET
import retrofit2.http.Url

interface YoutubeApi {

    @GET
    suspend fun getYoutubePlaylist(
        @Url url: String,
    ): YoutubePlaylistItems

}