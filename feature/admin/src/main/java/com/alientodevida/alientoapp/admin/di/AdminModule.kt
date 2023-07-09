package com.alientodevida.alientoapp.admin.di

import com.alientodevida.alientoapp.admin.data.AdminApi
import com.alientodevida.alientoapp.admin.data.AdminRepositoryImpl
import com.alientodevida.alientoapp.data.di.DataModule
import com.alientodevida.alientoapp.domain.di.BaseUrl
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
object AdminModule {

    @OptIn(ExperimentalSerializationApi::class)
    @Singleton
    @Provides
    fun adminApi(
        @BaseUrl
        baseUrl: String,
        @Named("Client")
        okHttpClient: OkHttpClient,
        json: Json,
    ): AdminApi = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(baseUrl)
        .addConverterFactory(json.asConverterFactory(DataModule.contentType))
        .build()
        .create(AdminApi::class.java)

    @Singleton
    @Provides
    fun campusRepository(
        adminApi: AdminApi,
    ): com.alientodevida.alientoapp.admin.domain.AdminRepository = AdminRepositoryImpl(adminApi)
}
