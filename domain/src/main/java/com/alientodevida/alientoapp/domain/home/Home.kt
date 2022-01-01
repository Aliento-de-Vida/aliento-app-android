package com.alientodevida.alientoapp.domain.home

import kotlinx.serialization.Serializable

@Serializable
data class Home(
  val ebook: String,
  val youtubePlaylistId: String,
  val youtubeChannelId: String,
  val spotifyPlaylistId: String,
  val prayerEmail: String,
  val socialMedia: SocialMedia,
)

@Serializable
data class SocialMedia(
  val instagramUrl: String,
  val youtubeChannelUrl: String,
  val facebookPageId: String,
  val facebookPageUrl: String,
  val twitterUserId: String,
  val twitterUrl: String,
  val spotifyArtistId: String,
)