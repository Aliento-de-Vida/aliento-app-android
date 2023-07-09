package com.alientodevida.alientoapp.app

import android.app.Application
import android.content.Context
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import com.alientodevida.alientoapp.domain.preferences.Preferences
import com.alientodevida.alientoapp.ui.coil.ResponseHeaderInterceptor
import com.alientodevida.alientoapp.ui.messaging.NotificationsManager
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient
import javax.inject.Inject

@HiltAndroidApp
class AppController : Application(), ImageLoaderFactory {

    @Inject
    lateinit var notificationsManager: NotificationsManager

    @Inject
    lateinit var preferences: Preferences

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        notificationsManager.subscribeToPushNotifications(preferences.pushEnabled)
    }

    override fun newImageLoader(): ImageLoader = imageLoader
}

val Context.imageLoader: ImageLoader
    get() = ImageLoader.Builder(this)
        .okHttpClient { // TODO change this for https://coil-kt.github.io/coil/image_pipeline/#keyers
            OkHttpClient.Builder()
                .addNetworkInterceptor(
                    ResponseHeaderInterceptor("Cache-Control", "max-age=31536000,public"),
                ).build()
        }
        .memoryCache { MemoryCache.Builder(this).build() }
        .diskCache {
            DiskCache.Builder().directory(this.cacheDir.resolve("image_cache")).build()
        }
        .build()
