package com.alientodevida.alientoapp.data.preferences

import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.alientodevida.alientoapp.domain.common.Home
import com.alientodevida.alientoapp.domain.common.SpotifyToken
import com.alientodevida.alientoapp.domain.preferences.Preferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import androidx.datastore.preferences.core.Preferences as DataStorePreferences
import com.alientodevida.alientoapp.domain.common.Token as AdminToken

class PreferencesImpl(
  private val preferences: SharedPreferences,
  private val preferencesStore: DataStore<DataStorePreferences>, // TODO use datastore correctly and remove sharedpreferences
) : Preferences {
  companion object {
    private val ADMIN_TOKEN = stringPreferencesKey("admin-token")
    private val DARK_THEME = booleanPreferencesKey("dark-theme")
    private val PUSH_NOTIFICATIONS = booleanPreferencesKey("push-notifications")
    
    private const val SPOTIFY_TOKEN_KEY = "spotify-token"
    private const val HOME_KEY = "home"
  }
  
  // Dark Theme
  override var isDarkTheme: Boolean
    get() = runBlocking { preferencesStore.data.map { it[DARK_THEME] ?: false }.first() }
    set(value) {
      runBlocking {
        preferencesStore.edit { settings ->
          settings[DARK_THEME] = value
        }
      }
    }
  override var isDarkThemeFlow: Flow<Boolean> = preferencesStore.data.map { it[DARK_THEME] ?: false }
  
  // Push Notifications
  override var pushEnabled: Boolean
    get() = runBlocking { preferencesStore.data.map { it[PUSH_NOTIFICATIONS] ?: true }.first() }
    set(value) {
      runBlocking {
        preferencesStore.edit { settings ->
          settings[PUSH_NOTIFICATIONS] = value
        }
      }
    }
  override var pushEnabledFlow: Flow<Boolean> = preferencesStore.data.map { it[PUSH_NOTIFICATIONS] ?: false }
  
  // Admin Token
  private var adminJwtToken: String
    get() = runBlocking { preferencesStore.data.map { it[ADMIN_TOKEN] ?: "" }.first() }
    set(value) {
      runBlocking {
        preferencesStore.edit { settings ->
          settings[ADMIN_TOKEN] = value
        }
      }
    }
  
  override var adminToken: AdminToken?
    get() = if (adminJwtToken.isNotBlank()) AdminToken(adminJwtToken) else null
    set(value) {
      adminJwtToken = value?.jwt ?: ""
    }
  
  override suspend fun isAdmin() = isAdminFlow.first()
  override val isAdminFlow: Flow<Boolean> =
    preferencesStore.data.map { it[ADMIN_TOKEN]?.isNotBlank() ?: false }
  
  // Spotify Token
  override var spotifyToken: SpotifyToken?
    get() = get(SPOTIFY_TOKEN_KEY, preferences)
    set(value) = value?.let { save(it, SPOTIFY_TOKEN_KEY) }
      ?: run { clear(SPOTIFY_TOKEN_KEY) }
  
  // Home
  override var home: Home?
    get() = get(HOME_KEY, preferences)
    set(value) = value?.let { save(it, HOME_KEY) }
      ?: run { clear(HOME_KEY) }
  
  // Helper
  override suspend fun clear() {
    clear(SPOTIFY_TOKEN_KEY)
  }
  
  private fun clear(name: String) {
    preferences.edit { remove(name) }
  }
  
  inline fun <reified T> get(name: String, preferences: SharedPreferences): T? {
    val stringObject = preferences.getString(name, Preferences.DEFAULT_STRING)!!
    return if (stringObject.isNotBlank()) {
      val collectionType = object : TypeToken<T>() {}.type
      Gson().fromJson(stringObject, collectionType)
    } else {
      null
    }
  }
  
  @SuppressLint("ApplySharedPref")
  private fun save(data: Any, key: String) {
    val editor = preferences.edit()
    val stringJson = Gson().toJson(data)
    editor?.putString(key, stringJson)?.commit()
  }
}
