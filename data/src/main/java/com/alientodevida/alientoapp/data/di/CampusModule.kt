package com.alientodevida.alientoapp.data.di

import com.alientodevida.alientoapp.data.campus.CampusAdminApi
import com.alientodevida.alientoapp.data.campus.CampusApi
import com.alientodevida.alientoapp.data.campus.CampusRepositoryImpl
import com.alientodevida.alientoapp.domain.campus.CampusRepository
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
object CampusModule {
  
  @Singleton
  @Provides
  fun campusApi(
    @Named("Client")
    okHttpClient: OkHttpClient,
    json: Json,
  ): CampusApi = Retrofit.Builder()
    .client(okHttpClient)
    .baseUrl("https://todoserver-peter.herokuapp.com")
    .addConverterFactory(json.asConverterFactory(DataModule.contentType))
    .build()
    .create(CampusApi::class.java)
  
  @Singleton
  @Provides
  fun campusAdminApi(
    @Named("AdminAuthClient")
    okHttpClient: OkHttpClient,
    json: Json,
  ): CampusAdminApi = Retrofit.Builder()
    .client(okHttpClient)
    .baseUrl("https://todoserver-peter.herokuapp.com")
    .addConverterFactory(json.asConverterFactory(DataModule.contentType))
    .build()
    .create(CampusAdminApi::class.java)
  
  @Singleton
  @Provides
  fun campusRepository(
    campusApi: CampusApi,
    campusAdminApi: CampusAdminApi,
    fileRepository: FileRepository,
  ): CampusRepository = CampusRepositoryImpl(campusApi, campusAdminApi, fileRepository)
  
}