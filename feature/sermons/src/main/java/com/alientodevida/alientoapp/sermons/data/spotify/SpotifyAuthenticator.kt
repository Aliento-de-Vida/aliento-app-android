package com.alientodevida.alientoapp.sermons.data.spotify

import com.alientodevida.alientoapp.domain.common.SpotifyToken
import com.alientodevida.alientoapp.domain.preferences.Preferences
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class SpotifyAuthenticator @Inject constructor(
    private val preferences: Preferences,
    private val spotifyAuthApi: SpotifyAuthApi,
) : Authenticator {
  
  val SPOTIFY_TOKEN =
    "Basic ZTBlNDMyMWYxNTI4NGU5YzkwNzRmMDFjNjAwOTdkOGY6YTQyNjk2MzViYzMyNDkxNjlkNjRhZWYzZTgwNGM1NGM="
  val SPOTIFY_GRANT_TYPE = "client_credentials"
  
  override fun authenticate(route: Route?, response: Response): Request? {
    if (response.code == 401) {
      var newSpotifyToken: SpotifyToken
      runBlocking {
        newSpotifyToken = spotifyAuthApi.getToken(
          "https://accounts.spotify.com/api/token/",
          SPOTIFY_TOKEN,
          SPOTIFY_GRANT_TYPE
        )
      }
      preferences.spotifyToken = newSpotifyToken
      return response.request.newBuilder().header(
        "Authorization", "Bearer ${newSpotifyToken.accessToken}"
      ).build()
      
    } else {
      return response.request.newBuilder().header(
        "Authorization", "Bearer ${preferences.spotifyToken?.accessToken}"
      ).build()
    }
  }
}