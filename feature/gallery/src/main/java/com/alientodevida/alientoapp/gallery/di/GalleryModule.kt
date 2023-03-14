package com.alientodevida.alientoapp.gallery.di

import com.alientodevida.alientoapp.data.di.DataModule
import com.alientodevida.alientoapp.domain.file.FileRepository
import com.alientodevida.alientoapp.gallery.data.GalleryAdminApi
import com.alientodevida.alientoapp.gallery.data.GalleryApi
import com.alientodevida.alientoapp.gallery.data.GalleryRepositoryImpl
import com.alientodevida.alientoapp.gallery.domain.GalleryRepository
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
object GalleryModule {

    @OptIn(ExperimentalSerializationApi::class)
    @Singleton
    @Provides
    fun galleryApi(
        @Named("base-url")
        baseUrl: String,
        @Named("Client")
        okHttpClient: OkHttpClient,
        json: Json,
    ): GalleryApi = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(baseUrl)
        .addConverterFactory(json.asConverterFactory(DataModule.contentType))
        .build()
        .create(GalleryApi::class.java)

    @OptIn(ExperimentalSerializationApi::class)
    @Singleton
    @Provides
    fun galleryAdminApi(
        @Named("base-url")
        baseUrl: String,
        @Named("AdminAuthClient")
        okHttpClient: OkHttpClient,
        json: Json,
    ): GalleryAdminApi = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(baseUrl)
        .addConverterFactory(json.asConverterFactory(DataModule.contentType))
        .build()
        .create(GalleryAdminApi::class.java)

    @Singleton
    @Provides
    fun galleryRepository(
        galleryApi: GalleryApi,
        galleryAdminApi: GalleryAdminApi,
        fileRepository: FileRepository,
    ): GalleryRepository =
        GalleryRepositoryImpl(
            galleryApi,
            galleryAdminApi,
            fileRepository
        )
}