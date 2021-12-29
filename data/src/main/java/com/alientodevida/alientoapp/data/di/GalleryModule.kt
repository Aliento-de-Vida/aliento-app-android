package com.alientodevida.alientoapp.data.di

import com.alientodevida.alientoapp.data.gallery.GalleryApi
import com.alientodevida.alientoapp.data.gallery.GalleryRepositoryImpl
import com.alientodevida.alientoapp.domain.gallery.GalleryRepository
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
object GalleryModule {
  
  @Singleton
  @Provides
  fun galleryApi(
    @Named("Client")
    okHttpClient: OkHttpClient,
    json: Json,
  ): GalleryApi = Retrofit.Builder()
    .client(okHttpClient)
    .baseUrl("https://todoserver-peter.herokuapp.com")
    .addConverterFactory(json.asConverterFactory(DataModule.contentType))
    .build()
    .create(GalleryApi::class.java)
  
  @Singleton
  @Provides
  fun galleryRepository(
    galleryApi: GalleryApi,
  ): GalleryRepository = GalleryRepositoryImpl(galleryApi)
  
}