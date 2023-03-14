package com.alientodevida.alientoapp.data.di

import com.alientodevida.alientoapp.data.file.FileApi
import com.alientodevida.alientoapp.data.file.FileRepositoryImpl
import com.alientodevida.alientoapp.domain.file.FileRepository
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
object FileModule {

    @Singleton
    @Provides
    fun fileApi(
        @Named("base-url")
        baseUrl: String,
        @Named("AdminAuthClient")
        okHttpClient: OkHttpClient,
        json: Json,
    ): FileApi = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(baseUrl)
        .addConverterFactory(json.asConverterFactory(DataModule.contentType))
        .build()
        .create(FileApi::class.java)

    @Singleton
    @Provides
    fun fileRepository(
        fileApi: FileApi,
    ): FileRepository = FileRepositoryImpl(fileApi)
}