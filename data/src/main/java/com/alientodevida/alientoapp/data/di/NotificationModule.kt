package com.alientodevida.alientoapp.data.di

import com.alientodevida.alientoapp.data.notification.NotificationAdminApi
import com.alientodevida.alientoapp.data.notification.NotificationApi
import com.alientodevida.alientoapp.data.notification.NotificationRepositoryImpl
import com.alientodevida.alientoapp.domain.file.FileRepository
import com.alientodevida.alientoapp.domain.notification.NotificationRepository
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
object NotificationModule {
  
  @Singleton
  @Provides
  fun notificationApi(
    @Named("Client")
    okHttpClient: OkHttpClient,
    json: Json,
  ): NotificationApi = Retrofit.Builder()
    .client(okHttpClient)
    .baseUrl("https://todoserver-peter.herokuapp.com")
    .addConverterFactory(json.asConverterFactory(DataModule.contentType))
    .build()
    .create(NotificationApi::class.java)
  
  @Singleton
  @Provides
  fun notificationAdminApi(
    @Named("AdminAuthClient")
    okHttpClient: OkHttpClient,
    json: Json,
  ): NotificationAdminApi = Retrofit.Builder()
    .client(okHttpClient)
    .baseUrl("https://todoserver-peter.herokuapp.com")
    .addConverterFactory(json.asConverterFactory(DataModule.contentType))
    .build()
    .create(NotificationAdminApi::class.java)
  
  @Singleton
  @Provides
  fun notificationRepository(
    notificationApi: NotificationApi,
    notificationAdminApi: NotificationAdminApi,
    fileRepository: FileRepository,
  ): NotificationRepository = NotificationRepositoryImpl(
    notificationApi,
    notificationAdminApi,
    fileRepository,
  )
  
}