package com.alientodevida.alientoapp.data.di

import com.alientodevida.alientoapp.data.admin.AdminApi
import com.alientodevida.alientoapp.data.admin.AdminRepositoryImpl
import com.alientodevida.alientoapp.domain.admin.AdminRepository
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
object AdminModule {
  
  @Singleton
  @Provides
  fun adminApi(
    @Named("Client")
    okHttpClient: OkHttpClient,
    json: Json,
  ): AdminApi = Retrofit.Builder()
    .client(okHttpClient)
    .baseUrl("https://todoserver-peter.herokuapp.com")
    .addConverterFactory(json.asConverterFactory(DataModule.contentType))
    .build()
    .create(AdminApi::class.java)
  
  @Singleton
  @Provides
  fun campusRepository(
    adminApi: AdminApi,
  ): AdminRepository = AdminRepositoryImpl(adminApi)
  
}