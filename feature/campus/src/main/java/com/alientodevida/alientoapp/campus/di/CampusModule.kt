package com.alientodevida.alientoapp.campus.di

import com.alientodevida.alientoapp.campus.data.CampusAdminApi
import com.alientodevida.alientoapp.campus.data.CampusApi
import com.alientodevida.alientoapp.campus.data.CampusRepositoryImpl
import com.alientodevida.alientoapp.campus.domain.CampusRepository
import com.alientodevida.alientoapp.data.di.DataModule
import com.alientodevida.alientoapp.domain.file.FileRepository
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
object CampusModule {

    @OptIn(ExperimentalSerializationApi::class)
    @Singleton
    @Provides
    fun campusApi(
        @Named("base-url")
        baseUrl: String,
        @Named("Client")
        okHttpClient: OkHttpClient,
        json: Json,
    ): CampusApi = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(baseUrl)
        .addConverterFactory(json.asConverterFactory(DataModule.contentType))
        .build()
        .create(CampusApi::class.java)

    @OptIn(ExperimentalSerializationApi::class)
    @Singleton
    @Provides
    fun campusAdminApi(
        @Named("base-url")
        baseUrl: String,
        @Named("AdminAuthClient")
        okHttpClient: OkHttpClient,
        json: Json,
    ): CampusAdminApi = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(baseUrl)
        .addConverterFactory(json.asConverterFactory(DataModule.contentType))
        .build()
        .create(CampusAdminApi::class.java)

    @Singleton
    @Provides
    fun campusRepository(
        campusApi: CampusApi,
        campusAdminApi: CampusAdminApi,
        fileRepository: FileRepository,
    ): CampusRepository = CampusRepositoryImpl(
        campusApi,
        campusAdminApi,
        fileRepository
    )
}