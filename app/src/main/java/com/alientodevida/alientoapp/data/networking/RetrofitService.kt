package com.alientodevida.alientoapp.data.networking

import com.alientodevida.alientoapp.data.entities.network.*
import retrofit2.http.*

const val BASE_URL_SPOTIFY_API = "https://api.spotify.com/"

interface RetrofitService {

    @GET
    suspend fun getYoutubePlaylist(
        @Url url: String,
    ): YoutubePlaylistItems

    @GET("/v1/shows/{podcast_id}/episodes/?market=MX")
    suspend fun getPodcast(
        @Header("Authorization") authorization: String,
        @Path("podcast_id") podcast_id: String
    ): Podcast

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

    @POST
    suspend fun getCsrfToken(
        @Url url: String,
        ): CsrfToken

    @POST
    suspend fun getTransmision(
        @Url url: String,
        ): Transmision

    @POST
    @FormUrlEncoded
    suspend fun sendPrayerRequest(
        @Url url: String,
        @Field("csrf-token") csrfToken: String,
        @Field("asunto") asunto: String,
        @Field("nombre") nombre: String,
        @Field("email") email: String,
        @Field("whatsapp") whatsapp: String,
        @Field("mensaje") mensaje: String
    ): AskPrayerResponse

    /*@GET("/v1/playlists/{playlist_id}/")
    suspend fun getPlaylist(
        @Header("Authorization") authorization: String,
        @Path("playlist_id") playlistId: String
    ): PlayList*/


}