package com.alientodevida.alientoapp.data.spotify

import com.alientodevida.alientoapp.domain.entities.network.Podcast
import retrofit2.http.GET
import retrofit2.http.Path

interface SpotifyApi {

    @GET("/v1/shows/{podcast_id}/episodes/?market=MX")
    suspend fun getPodcast(
        @Path("podcast_id") podcast_id: String
    ): Podcast

    /*@GET("/v1/playlists/{playlist_id}/")
    suspend fun getPlaylist(
        @Header("Authorization") authorization: String,
        @Path("playlist_id") playlistId: String
    ): PlayList*/

}