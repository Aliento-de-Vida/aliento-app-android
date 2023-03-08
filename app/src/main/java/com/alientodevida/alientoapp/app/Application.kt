package com.alientodevida.alientoapp.app

import android.app.Application
import android.content.Context
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import com.alientodevida.alientoapp.ui.messaging.NotificationsService
import com.alientodevida.alientoapp.ui.coil.ResponseHeaderInterceptor
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient
import javax.inject.Inject

@HiltAndroidApp
class AppController : Application(), ImageLoaderFactory {

  @Inject
  lateinit var notificationsService: NotificationsService

  override fun onCreate() {
    super.onCreate()
    notificationsService.setActivityClass(MainActivity::class.java)
  }

  override fun newImageLoader(): ImageLoader  = imageLoader
  
}

val Context.imageLoader: ImageLoader
  get() = ImageLoader.Builder(this)
    .okHttpClient { // TODO change this for https://coil-kt.github.io/coil/image_pipeline/#keyers
      OkHttpClient.Builder()
        .addNetworkInterceptor(
          ResponseHeaderInterceptor("Cache-Control", "max-age=31536000,public")
        ).build()
    }
    .memoryCache { MemoryCache.Builder(this).build() }
    .diskCache {
      DiskCache.Builder().directory(this.cacheDir.resolve("image_cache")).build()
    }
    .build()