package com.alientodevida.alientoapp.sermons.data.spotify

import com.alientodevida.alientoapp.domain.preferences.Preferences
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class SpotifyAuthenticatorInterceptor @Inject constructor(
  private val preferences: Preferences
) : Interceptor {
  
  override fun intercept(chain: Interceptor.Chain): Response {
    return try {
      val requestBuilder = chain.request().newBuilder()
      
      preferences.spotifyToken?.let {
        if (it.accessToken.isNotEmpty()) {
          requestBuilder.addHeader("Authorization", "Bearer ${it.accessToken}")
        }
      }
      
      chain.proceed(requestBuilder.build())
      
    } catch (e: Exception) {
      e.printStackTrace()
      chain.proceed(chain.request().newBuilder().build())
    }
  }
  
}