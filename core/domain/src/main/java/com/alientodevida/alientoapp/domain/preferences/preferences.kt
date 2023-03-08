package com.alientodevida.alientoapp.domain.preferences

import com.alientodevida.alientoapp.domain.common.Home
import com.alientodevida.alientoapp.domain.common.SpotifyToken
import kotlinx.coroutines.flow.Flow
import com.alientodevida.alientoapp.domain.common.Token as AdminToken

interface Preferences {
  companion object {
    const val DEFAULT_BOOLEAN = false
    const val DEFAULT_INT = 0
    const val DEFAULT_LONG = 0L
    const val DEFAULT_FLOAT = 0.0f
    const val DEFAULT_STRING = ""
  }
  
  var isDarkTheme: Boolean
  var isDarkThemeFlow: Flow<Boolean>
  
  suspend fun isAdmin(): Boolean
  val isAdminFlow: Flow<Boolean>
  
  var pushEnabled: Boolean
  var pushEnabledFlow: Flow<Boolean>
  
  var spotifyToken: SpotifyToken?
  var adminToken: AdminToken?
  
  var home: Home?
  
  suspend fun clear()
}