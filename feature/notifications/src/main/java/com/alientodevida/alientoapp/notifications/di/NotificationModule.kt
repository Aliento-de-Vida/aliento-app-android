package com.alientodevida.alientoapp.notifications.di

import com.alientodevida.alientoapp.data.di.DataModule
import com.alientodevida.alientoapp.domain.file.FileRepository
import com.alientodevida.alientoapp.notifications.data.NotificationAdminApi
import com.alientodevida.alientoapp.notifications.data.NotificationApi
import com.alientodevida.alientoapp.notifications.data.NotificationRepositoryImpl
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
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
object NotificationModule {
  
  @OptIn(ExperimentalSerializationApi::class)
  @Singleton
  @Provides
  fun notificationApi(
    @Named("base-url")
    baseUrl: String,
    @Named("Client")
    okHttpClient: OkHttpClient,
    json: Json,
  ): NotificationApi = Retrofit.Builder()
    .client(okHttpClient)
    .baseUrl(baseUrl)
    .addConverterFactory(json.asConverterFactory(DataModule.contentType))
    .build()
    .create(NotificationApi::class.java)
  
  @OptIn(ExperimentalSerializationApi::class)
  @Singleton
  @Provides
  fun notificationAdminApi(
    @Named("base-url")
    baseUrl: String,
    @Named("AdminAuthClient")
    okHttpClient: OkHttpClient,
    json: Json,
  ): NotificationAdminApi = Retrofit.Builder()
    .client(okHttpClient)
    .baseUrl(baseUrl)
    .addConverterFactory(json.asConverterFactory(DataModule.contentType))
    .build()
    .create(NotificationAdminApi::class.java)
  
  @Singleton
  @Provides
  fun notificationRepository(
      notificationApi: NotificationApi,
      notificationAdminApi: NotificationAdminApi,
      fileRepository: FileRepository,
  ): com.alientodevida.alientoapp.notifications.domain.NotificationRepository = NotificationRepositoryImpl(
    notificationApi,
    notificationAdminApi,
    fileRepository,
  )
  
}