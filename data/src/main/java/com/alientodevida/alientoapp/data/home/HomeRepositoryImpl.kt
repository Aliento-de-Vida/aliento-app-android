package com.alientodevida.alientoapp.data.home

import com.alientodevida.alientoapp.domain.home.Home
import com.alientodevida.alientoapp.domain.home.HomeRepository

class HomeRepositoryImpl(
  private val api: HomeApi,
) : HomeRepository {
  override suspend fun getHome() = api.getHome()
  
  override suspend fun updateHome(home: Home): Home =
    api.updateHome(
      ebook = home.ebook,
      youtubePlaylistId = home.youtubePlaylistId,
      youtubeChannelId = home.youtubeChannelId,
      spotifyPlaylistId = home.spotifyPlaylistId,
      prayerEmail = home.prayerEmail,
      instagramUrl = home.socialMedia.instagramUrl,
      youtubeChannelUrl = home.socialMedia.youtubeChannelUrl,
      facebookPageId = home.socialMedia.facebookPageId,
      facebookPageUrl = home.socialMedia.facebookPageUrl,
      twitterUserId = home.socialMedia.twitterUserId,
      twitterUrl = home.socialMedia.twitterUrl,
      spotifyArtistId = home.socialMedia.spotifyArtistId,
    )
  
}