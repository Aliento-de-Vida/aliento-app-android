package com.alientodevida.alientoapp.sermons.data.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alientodevida.alientoapp.sermons.domain.audio.Audio
import com.alientodevida.alientoapp.domain.video.YoutubeVideo

@Database(entities = [YoutubeVideo::class, Audio::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
  abstract val roomDao: RoomDao
}

private lateinit var INSTANCE: AppDatabase

fun getDatabase(context: Context): AppDatabase {
  synchronized(AppDatabase::class.java) {
    if (!::INSTANCE.isInitialized) {
      INSTANCE = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        "appdatabase"
      ).build()
    }
  }
  return INSTANCE
}