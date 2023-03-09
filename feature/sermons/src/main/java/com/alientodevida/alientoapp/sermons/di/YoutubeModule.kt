package com.alientodevida.alientoapp.sermons.di

import com.alientodevida.alientoapp.data.di.DataModule
import com.alientodevida.alientoapp.domain.video.VideoRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object YoutubeModule {
  
  @OptIn(ExperimentalSerializationApi::class)
  @Singleton
  @Provides
  fun youtubeApi(
    @Named("Client")
    okHttpClient: OkHttpClient,
    json: Json,
  ): com.alientodevida.alientoapp.sermons.data.video.YoutubeApi = Retrofit.Builder()
    .client(okHttpClient)
    .baseUrl("https://www.googleapis.com")
    .addConverterFactory(json.asConverterFactory(DataModule.contentType))
    .build()
    .create(com.alientodevida.alientoapp.sermons.data.video.YoutubeApi::class.java)

}

@Module
@InstallIn(SingletonComponent::class)
abstract class YoutubeBindsModule {

  @Binds
  abstract fun videoRepository(
    videoRepositoryImpl: com.alientodevida.alientoapp.sermons.data.video.VideoRepositoryImpl
  ): VideoRepository

}