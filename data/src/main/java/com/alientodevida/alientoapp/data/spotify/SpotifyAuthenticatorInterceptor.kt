package com.alientodevida.alientoapp.data.spotify

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

			// If token has been saved, add it to the request
			preferences.spotifyJwtToken?.let {
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