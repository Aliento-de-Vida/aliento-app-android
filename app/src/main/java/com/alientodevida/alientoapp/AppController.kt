package com.alientodevida.alientoapp

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.HiltAndroidApp

const val PREFS_NAME = "SHARED_PREFERENCES"

@HiltAndroidApp
class AppController: Application() {
    init {
        instance = this
    }

    companion object {
        private var instance: AppController? = null
        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
        val context: Context?
            get() = instance


        fun save(data: Any, key: String) {
            val prefs = instance!!.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            val editor = prefs?.edit()
            val stringJson = Gson().toJson(data)
            editor?.putString(key, stringJson)?.commit()
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