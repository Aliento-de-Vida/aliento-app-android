package com.alientodevida.alientoapp.sermons.data.spotify

import com.alientodevida.alientoapp.sermons.domain.audio.Albums
import com.alientodevida.alientoapp.sermons.domain.audio.Podcasts
import com.alientodevida.alientoapp.sermons.domain.audio.Tracks
import retrofit2.http.GET
import retrofit2.http.Path

interface SpotifyApi {

    @GET("/v1/shows/{podcast_id}/episodes/?market=MX")
    suspend fun getPodcast(
        @Path("podcast_id") podcast_id: String
    ): Podcasts

    @GET("/v1/artists/{artist_id}/albums")
    suspend fun getAlbums(
        @Path("artist_id") artist_id: String
    ): Albums

    @GET("/v1/albums/{album_id}/tracks")
    suspend fun getTracks(
        @Path("album_id") album_id: String
    ): Tracks

    /*@GET("/v1/playlists/{playlist_id}/")
    suspend fun getPlaylist(
        @Header("Authorization") authorization: String,
        @Path("playlist_id") playlistId: String
    ): PlayList*/
}