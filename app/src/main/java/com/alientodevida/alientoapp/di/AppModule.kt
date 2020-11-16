package com.alientodevida.alientoapp.di

import android.content.Context
import com.alientodevida.alientoapp.BuildConfig
import com.alientodevida.alientoapp.data.domain.Repository
import com.alientodevida.alientoapp.data.networking.BASE_URL_SPOTIFY_API
import com.alientodevida.alientoapp.data.networking.RetrofitService
import com.alientodevida.alientoapp.data.repository.RepositoryImpl
import com.alientodevida.alientoapp.data.storage.RoomDao
import com.alientodevida.alientoapp.data.storage.AppDatabase
import com.alientodevida.alientoapp.data.storage.getDatabase
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
abstract class AppModule {

}

@Module
@InstallIn(ApplicationComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindRepository(impl: RepositoryImpl): Repository

}

@Module
@InstallIn(ApplicationComponent::class)
class LocalModule {

    @Singleton
    @Provides
    fun providesDatabase(@ApplicationContext context: Context): AppDatabase = getDatabase(context)

    @Singleton
    @Provides
    fun videoDAO(database: AppDatabase): RoomDao = database.roomDao
}

@Module
@InstallIn(ApplicationComponent::class)
class NetworkModule {

    @Provides
    fun providesRetrofitService(): RetrofitService {
        val httpBuilder: OkHttpClient.Builder = OkHttpClient.Builder()

        addLoggingInterceptor(httpBuilder)

        val httpClient = httpBuilder
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL_SPOTIFY_API)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(httpClient)
            .build()

        return retrofit.create(RetrofitService::class.java)
    }

    private fun addLoggingInterceptor(httpBuilder: OkHttpClient.Builder) {
        val logging = HttpLoggingInterceptor()
        logging.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        httpBuilder.addInterceptor(logging)
    }
}