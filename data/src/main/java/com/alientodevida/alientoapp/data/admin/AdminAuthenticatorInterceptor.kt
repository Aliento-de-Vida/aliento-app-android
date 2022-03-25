package com.alientodevida.alientoapp.data.admin

import com.alientodevida.alientoapp.domain.preferences.Preferences
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AdminAuthenticatorInterceptor @Inject constructor(
  private val preferences: Preferences
) : Interceptor {
  
  override fun intercept(chain: Interceptor.Chain): Response {
    return try {
      val requestBuilder = chain.request().newBuilder()
      
      preferences.adminToken?.let {
        requestBuilder.addHeader("Authorization", "Bearer ${it.jwt}")
      }
      
      chain.proceed(requestBuilder.build())
      
    } catch (e: Exception) {
      e.printStackTrace()
      chain.proceed(chain.request().newBuilder().build())
    }
  }
  
}