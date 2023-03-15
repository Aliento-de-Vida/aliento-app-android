package com.alientodevida.alientoapp.sermons.data.video

import com.alientodevida.alientoapp.sermons.domain.video.YoutubeChannelItems
import com.alientodevida.alientoapp.sermons.domain.video.YoutubePlaylistItems
import retrofit2.http.GET
import retrofit2.http.Url

interface YoutubeApi {

    @GET
    suspend fun getYoutubePlaylist(
        @Url url: String,
    ): YoutubePlaylistItems

    @GET
    suspend fun getYoutubeChannelVideos(
        @Url url: String,
    ): YoutubeChannelItems
}
