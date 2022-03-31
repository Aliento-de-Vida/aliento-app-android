package com.alientodevida.alientoapp.data.preferences

import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alientodevida.alientoapp.domain.home.Home
import com.alientodevida.alientoapp.domain.preferences.Preferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import java.util.*
import androidx.datastore.preferences.core.Preferences as DataStorePreferences
import com.alientodevida.alientoapp.domain.admin.Token as AdminToken
import com.alientodevida.alientoapp.domain.entities.network.Token as SpotifyToken

class PreferencesImpl(
  private val preferences: SharedPreferences,
  private val preferencesStore: DataStore<DataStorePreferences>,
) : Preferences {
  companion object {
    private val ADMIN_TOKEN = stringPreferencesKey("admin-token")
    private const val SPOTIFY_TOKEN_KEY = "spotify-token"
    private const val PUSH_ENABLED_KEY = "push-enabled"
    private const val HOME_KEY = "home"
    private const val NIGHT_MODE_KEY = "night_mode"
    private const val NIGHT_MODE_DEF_VAL = AppCompatDelegate.MODE_NIGHT_NO
  }
  
  private val nightMode: Int
    get() = preferences.getInt(NIGHT_MODE_KEY, NIGHT_MODE_DEF_VAL)
  
  private val _nightModeLive: MutableLiveData<Int> = MutableLiveData()
  override val nightModeLive: LiveData<Int>
    get() = _nightModeLive
  
  override var isDarkTheme: Boolean = false
    get() = nightMode == AppCompatDelegate.MODE_NIGHT_YES
    set(value) {
      preferences.edit().putInt(
        NIGHT_MODE_KEY, if (value) {
          AppCompatDelegate.MODE_NIGHT_YES
        } else {
          AppCompatDelegate.MODE_NIGHT_NO
        }
      ).apply()
      field = value
    }
  
  private val _isDarkThemeLive: MutableLiveData<Boolean> = MutableLiveData()
  override val isDarkThemeLive: LiveData<Boolean>
    get() = _isDarkThemeLive
  
  private val preferenceChangedListener =
    SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
      when (key) {
        NIGHT_MODE_KEY -> {
          _nightModeLive.value = nightMode
          _isDarkThemeLive.value = isDarkTheme
        }
      }
    }
  
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
  
  private val _pushEnabled =
    PrimitivePreferenceProperty<Boolean>(Type.BOOLEAN, PUSH_ENABLED_KEY, preferences)
  override var pushEnabled: Boolean by _pushEnabled
  
  init {
    if (!preferences.contains(NIGHT_MODE_KEY)) {
      isDarkTheme = getRandomBoolean()
    }
    
    if (!preferences.contains(PUSH_ENABLED_KEY)) {
      pushEnabled = true
    }
    
    _nightModeLive.value = nightMode
    _isDarkThemeLive.value = isDarkTheme
    
    preferences.registerOnSharedPreferenceChangeListener(preferenceChangedListener)
  }
  
  private fun getRandomBoolean() = (Random().nextInt(2) + 1) == 1
  
  override var spotifyToken: SpotifyToken?
    get() = get(SPOTIFY_TOKEN_KEY, preferences)
    set(value) = value?.let { save(it, SPOTIFY_TOKEN_KEY) }
      ?: run { clear(SPOTIFY_TOKEN_KEY) }
  
  override var home: Home?
    get() = get(HOME_KEY, preferences)
    set(value) = value?.let { save(it, HOME_KEY) }
      ?: run { clear(HOME_KEY) }
  
  override suspend fun clear() {
    clear(SPOTIFY_TOKEN_KEY)
    _pushEnabled.clear()
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
