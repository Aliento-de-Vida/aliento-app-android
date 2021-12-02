package com.alientodevida.alientoapp.data.di

import com.alientodevida.alientoapp.data.home.HomeApi
import com.alientodevida.alientoapp.data.home.HomeRepositoryImpl
import com.alientodevida.alientoapp.domain.home.HomeRepository
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
object HomeModule {

	@Singleton
	@Provides
	fun homeApi(
		@Named("Client")
		okHttpClient: OkHttpClient,
		json: Json,
	): HomeApi = Retrofit.Builder()
		.client(okHttpClient)
		.baseUrl("https://todoserver-peter.herokuapp.com")
		.addConverterFactory(json.asConverterFactory(DataModule.contentType))
		.build()
		.create(HomeApi::class.java)

	@Singleton
	@Provides
	fun homeRepository(
		homeApi: HomeApi,
	): HomeRepository = HomeRepositoryImpl(homeApi)

}