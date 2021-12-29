package com.alientodevida.alientoapp.data.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alientodevida.alientoapp.domain.entities.local.ImageUrlEntity
import com.alientodevida.alientoapp.domain.entities.local.Podcast
import com.alientodevida.alientoapp.domain.entities.local.YoutubeVideo

@Database(entities = [YoutubeVideo::class, Podcast::class, ImageUrlEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract val roomDao: RoomDao
}

private lateinit var INSTANCE: AppDatabase

fun getDatabase(context: Context): AppDatabase {
    synchronized(AppDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java,
                "appdatabase").build()
        }
    }
    return INSTANCE
}