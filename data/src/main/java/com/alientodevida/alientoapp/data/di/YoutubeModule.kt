package com.alientodevida.alientoapp.data.di

import com.alientodevida.alientoapp.data.storage.RoomDao
import com.alientodevida.alientoapp.data.youtube.VideoRepositoryImpl
import com.alientodevida.alientoapp.data.youtube.YoutubeApi
import com.alientodevida.alientoapp.domain.video.VideoRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object YoutubeModule {
  
  @Singleton
  @Provides
  fun youtubeApi(
    @Named("Client")
    okHttpClient: OkHttpClient,
    json: Json,
  ): YoutubeApi = Retrofit.Builder()
    .client(okHttpClient)
    .baseUrl("https://www.googleapis.com")
    .addConverterFactory(json.asConverterFactory(DataModule.contentType))
    .build()
    .create(YoutubeApi::class.java)
  
  @Singleton
  @Provides
  fun youtubeRepository(
    youtubeApi: YoutubeApi,
    roomDao: RoomDao
  ): VideoRepository = VideoRepositoryImpl(youtubeApi, roomDao)
  
}