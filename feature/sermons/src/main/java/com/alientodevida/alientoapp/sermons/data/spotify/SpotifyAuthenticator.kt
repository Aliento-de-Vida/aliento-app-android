package com.alientodevida.alientoapp.sermons.data.spotify

import com.alientodevida.alientoapp.domain.common.SpotifyToken
import com.alientodevida.alientoapp.domain.di.SpotifyBasicToken
import com.alientodevida.alientoapp.domain.di.YoutubeKey
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
    @SpotifyBasicToken private val spotifyBasicToken: String,
) : Authenticator {

    val SPOTIFY_GRANT_TYPE = "client_credentials"

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.code == 401) {
            var newSpotifyToken: SpotifyToken
            runBlocking {
                newSpotifyToken = spotifyAuthApi.getToken(
                    "https://accounts.spotify.com/api/token/",
                    spotifyBasicToken,
                    SPOTIFY_GRANT_TYPE,
                )
            }
            preferences.spotifyToken = newSpotifyToken
            return response.request.newBuilder().header(
                "Authorization",
                "Bearer ${newSpotifyToken.accessToken}",
            ).build()
        } else {
            return response.request.newBuilder().header(
                "Authorization",
                "Bearer ${preferences.spotifyToken?.accessToken}",
            ).build()
        }
    }
}
