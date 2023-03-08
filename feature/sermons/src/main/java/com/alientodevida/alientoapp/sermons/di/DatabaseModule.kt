package com.alientodevida.alientoapp.sermons.di

import android.content.Context
import com.alientodevida.alientoapp.sermons.data.storage.AppDatabase
import com.alientodevida.alientoapp.sermons.data.storage.RoomDao
import com.alientodevida.alientoapp.sermons.data.storage.getDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun providesDatabase(@ApplicationContext context: Context): AppDatabase =
        getDatabase(context)

    @Singleton
    @Provides
    fun dao(database: AppDatabase): RoomDao = database.roomDao

}