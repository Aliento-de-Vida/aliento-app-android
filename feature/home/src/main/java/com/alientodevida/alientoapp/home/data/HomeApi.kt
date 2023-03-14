package com.alientodevida.alientoapp.home.data

import com.alientodevida.alientoapp.domain.home.Home
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface HomeApi {

  @GET("/v1/home")
  suspend fun getHome(): Home

  @POST("/v1/home")
  @FormUrlEncoded
  suspend fun updateHome(
    @Field("ebook_url") ebook: String,
    @Field("youtube_playlist_id") youtubePlaylistId: String,
    @Field("youtube_channel_id") youtubeChannelId: String,
    @Field("spotify_playlist_id") spotifyPlaylistId: String,
    @Field("prayer_email") prayerEmail: String,
    @Field("instagram_url") instagramUrl: String,
    @Field("youtube_channel_url") youtubeChannelUrl: String,
    @Field("facebook_page_id") facebookPageId: String,
    @Field("facebook_page_url") facebookPageUrl: String,
    @Field("twitter_user_id") twitterUserId: String,
    @Field("twitter_url") twitterUrl: String,
    @Field("spotify_artist_id") spotifyArtistId: String,
  ): Home
}