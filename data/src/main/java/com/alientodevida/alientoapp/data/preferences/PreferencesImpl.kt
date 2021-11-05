package com.alientodevida.alientoapp.data.preferences

import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alientodevida.alientoapp.domain.entities.network.Token
import com.alientodevida.alientoapp.domain.preferences.Preferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class PreferencesImpl(
    private val preferences: SharedPreferences,
) : Preferences {
    companion object {
        const val JWT_TOKEN_KEY = "jwt-token"
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

    init {
        if (!preferences.contains(NIGHT_MODE_KEY)) {
            isDarkTheme = getRandomBoolean()
        }

        _nightModeLive.value = nightMode
        _isDarkThemeLive.value = isDarkTheme

        preferences.registerOnSharedPreferenceChangeListener(preferenceChangedListener)
    }

    private fun getRandomBoolean() = (Random().nextInt(2) + 1) == 1

    private val _pushToken =
        PrimitivePreferenceProperty<String>(Type.STRING, "push-token", preferences)
    override var pushToken: String by _pushToken


    override var jwtToken: Token?
        get() = get(JWT_TOKEN_KEY, preferences)
        set(value) = value?.let { save(it, JWT_TOKEN_KEY) }
            ?: run { clear(JWT_TOKEN_KEY) }

    override suspend fun clear() {
        clear(JWT_TOKEN_KEY)
        _pushToken.clear()
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
