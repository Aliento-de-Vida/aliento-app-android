package com.alientodevida.alientoapp.data.di

import android.content.Context
import com.alientodevida.alientoapp.common.logger.Logger
import com.alientodevida.alientoapp.data.file.FileApi
import com.alientodevida.alientoapp.data.file.FileRepositoryImpl
import com.alientodevida.alientoapp.domain.di.BaseUrl
import com.alientodevida.alientoapp.domain.file.FileRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
        @BaseUrl
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
        @ApplicationContext context: Context,
        fileApi: FileApi,
        logger: Logger,
    ): FileRepository = FileRepositoryImpl(context, fileApi, logger)
}
