package com.alientodevida.alientoapp.data.networking

import com.alientodevida.alientoapp.data.entities.ImageUrlResponse
import com.alientodevida.alientoapp.data.entities.PlayList
import com.alientodevida.alientoapp.data.entities.Podcast
import com.alientodevida.alientoapp.data.entities.Token
import retrofit2.http.*

const val BASE_URL_SPOTIFY_API = "https://api.spotify.com/"

interface RetrofitService {

    @GET
    suspend fun getImageUrl(
        @Url url: String,
        @Header("Authorization") authorization: String
    ): ImageUrlResponse

    @POST
    @FormUrlEncoded
    suspend fun getToken(
        @Url url: String,
        @Header("Authorization") authorization: String,
        @Field("grant_type") grantType: String
        ): Token

    @GET("/v1/playlists/{playlist_id}/")
    suspend fun getPlaylist(
        @Header("Authorization") authorization: String,
        @Path("playlist_id") playlistId: String
    ): PlayList

    @GET("/v1/shows/{podcast_id}/episodes/?market=MX")
    suspend fun getPodcast(
        @Header("Authorization") authorization: String,
        @Path("podcast_id") podcast_id: String
    ): Podcast
}