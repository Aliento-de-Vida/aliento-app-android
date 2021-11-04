package com.alientodevida.alientoapp.app.di

import android.content.Context
import android.content.SharedPreferences
import com.alientodevida.alientoapp.app.BuildConfig
import com.alientodevida.alientoapp.app.PREFS_NAME
import com.alientodevida.alientoapp.app.logger.LoggerImpl
import com.alientodevida.alientoapp.data.networking.BASE_URL_SPOTIFY_API
import com.alientodevida.alientoapp.data.networking.RetrofitService
import com.alientodevida.alientoapp.data.storage.AppDatabase
import com.alientodevida.alientoapp.data.storage.RoomDao
import com.alientodevida.alientoapp.data.storage.getDatabase
import com.alientodevida.alientoapp.domain.logger.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

}

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {

    @Singleton
    @Provides
    fun providesDatabase(@ApplicationContext context: Context): AppDatabase = getDatabase(context)

    @Singleton
    @Provides
    fun videoDAO(database: AppDatabase): RoomDao = database.roomDao

    @Provides
    fun providesSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun logger(): Logger = LoggerImpl()
}
