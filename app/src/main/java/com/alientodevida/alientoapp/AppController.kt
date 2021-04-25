package com.alientodevida.alientoapp

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

const val PREFS_NAME = "SHARED_PREFERENCES"

@HiltAndroidApp
class AppController: Application() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    init {
        instance = this
    }

    companion object {
        private lateinit var instance: AppController

        fun applicationContext() : Context {
            return instance.applicationContext
        }
        val context: Context
            get() = instance


        fun save(data: Any, key: String) {
            val editor = instance.sharedPreferences.edit()
            val stringJson = Gson().toJson(data)
            editor?.putString(key, stringJson)?.apply()
        }

        inline fun <reified T> get(key: String): T? {
            val prefs = applicationContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            val stringObject = prefs.getString(key, "")
            return if (stringObject?.isNotBlank()!!) {
                val collectionType = object : TypeToken<T>() {}.type
                Gson().fromJson(stringObject, collectionType)
            } else {
                null
            }
        }
    }
}