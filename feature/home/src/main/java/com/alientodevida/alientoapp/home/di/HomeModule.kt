package com.alientodevida.alientoapp.home.di

import com.alientodevida.alientoapp.data.di.DataModule
import com.alientodevida.alientoapp.domain.file.FileRepository
import com.alientodevida.alientoapp.domain.home.HomeRepository
import com.alientodevida.alientoapp.home.data.HomeApi
import com.alientodevida.alientoapp.home.data.HomeRepositoryImpl
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
object HomeModule {
  
  @OptIn(ExperimentalSerializationApi::class)
  @Singleton
  @Provides
  fun homeApi(
    @Named("base-url")
    baseUrl: String,
    @Named("Client")
    okHttpClient: OkHttpClient,
    json: Json,
  ): HomeApi = Retrofit.Builder()
    .client(okHttpClient)
    .baseUrl(baseUrl)
    .addConverterFactory(json.asConverterFactory(DataModule.contentType))
    .build()
    .create(HomeApi::class.java)
  
  @Singleton
  @Provides
  fun homeRepository(
      homeApi: HomeApi,
      filesRepository: FileRepository,
  ): HomeRepository =
    HomeRepositoryImpl(
      homeApi,
      filesRepository,
    )
  
}