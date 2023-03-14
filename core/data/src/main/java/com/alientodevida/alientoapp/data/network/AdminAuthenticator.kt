package com.alientodevida.alientoapp.data.network

import com.alientodevida.alientoapp.domain.preferences.Preferences
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class AdminAuthenticator @Inject constructor(
    private val preferences: Preferences,
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.code == 401) {
            preferences.adminToken = null
            return null
        } else {
            return response.request.newBuilder().header(
                "Authorization", "Bearer ${preferences.adminToken?.jwt}"
            ).build()
        }
    }
}