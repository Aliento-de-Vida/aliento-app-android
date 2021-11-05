package com.alientodevida.alientoapp.app

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.alientodevida.alientoapp.domain.preferences.Preferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class AppController : Application() { }