package com.alientodevida.alientoapp.domain.home

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Home(
  val ebook: String,
  val youtubePlaylistId: String,
  val youtubeChannelId: String,
  val spotifyPlaylistId: String,
  val prayerEmail: String,
  val socialMedia: SocialMedia,
): Parcelable {
  val isComplete: Boolean
    get() = ebook.isNotBlank() &&
        youtubePlaylistId.isNotBlank() &&
        youtubeChannelId.isNotBlank() &&
        spotifyPlaylistId.isNotBlank() &&
        prayerEmail.isNotBlank() &&
        socialMedia.isComplete
  
  companion object {
    fun empty() = Home(
      ebook = "",
      youtubePlaylistId = "",
      youtubeChannelId = "",
      spotifyPlaylistId = "",
      prayerEmail = "",
      socialMedia = SocialMedia(
        instagramUrl = "",
        youtubeChannelUrl = "",
        facebookPageId = "",
        facebookPageUrl = "",
        twitterUserId = "",
        twitterUrl = "",
        spotifyArtistId = "",
      ),
    )
  }
}

@Parcelize
@Serializable
data class SocialMedia(
  val instagramUrl: String,
  val youtubeChannelUrl: String,
  val facebookPageId: String,
  val facebookPageUrl: String,
  val twitterUserId: String,
  val twitterUrl: String,
  val spotifyArtistId: String,
): Parcelable {
  val isComplete: Boolean
    get() = instagramUrl.isNotBlank() &&
        youtubeChannelUrl.isNotBlank() &&
        facebookPageId.isNotBlank() &&
        facebookPageUrl.isNotBlank() &&
        twitterUserId.isNotBlank() &&
        twitterUrl.isNotBlank() &&
        spotifyArtistId.isNotBlank()
}