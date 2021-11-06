package com.alientodevida.alientoapp.domain.preferences

import androidx.lifecycle.LiveData
import com.alientodevida.alientoapp.domain.entities.network.Token

interface Preferences {
	companion object {
		const val DEFAULT_BOOLEAN = false
		const val DEFAULT_INT = 0
		const val DEFAULT_LONG = 0L
		const val DEFAULT_FLOAT = 0.0f
		const val DEFAULT_STRING = ""
	}

	val nightModeLive: LiveData<Int>
	var isDarkTheme: Boolean
	val isDarkThemeLive: LiveData<Boolean>

	var pushToken: String
	var spotifyJwtToken: Token?

	suspend fun clear()
}