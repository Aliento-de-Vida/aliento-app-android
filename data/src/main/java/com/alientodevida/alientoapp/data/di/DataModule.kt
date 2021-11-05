package com.alientodevida.alientoapp.data.di

import android.content.Context
import com.alientodevida.alientoapp.data.networking.BASE_URL_SPOTIFY_API
import com.alientodevida.alientoapp.data.networking.RetrofitService
import com.alientodevida.alientoapp.data.preferences.PreferencesImpl
import com.alientodevida.alientoapp.data.repository.RepositoryImpl
import com.alientodevida.alientoapp.data.storage.AppDatabase
import com.alientodevida.alientoapp.data.storage.RoomDao
import com.alientodevida.alientoapp.data.storage.getDatabase
import com.alientodevida.alientoapp.domain.Repository
import com.alientodevida.alientoapp.domain.preferences.Preferences
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

	@Singleton
	@Provides
	fun providesDatabase(@ApplicationContext context: Context): AppDatabase = getDatabase(context)

	@Singleton
	@Provides
	fun videoDAO(database: AppDatabase): RoomDao = database.roomDao

	@Singleton
	@Provides
	fun repository(
		retrofitService: RetrofitService,
		roomDao: RoomDao
	): Repository = RepositoryImpl(retrofitService, roomDao)
}

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

	@Provides
	fun providesRetrofitService(): RetrofitService {
		val httpBuilder: OkHttpClient.Builder = OkHttpClient.Builder()

		addLoggingInterceptor(httpBuilder)

		val httpClient = httpBuilder
			.connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS)
			.build()

		val retrofit = Retrofit.Builder()
			.baseUrl(BASE_URL_SPOTIFY_API)
			.addConverterFactory(MoshiConverterFactory.create())
			.client(httpClient)
			.build()

		return retrofit.create(RetrofitService::class.java)
	}

	private fun addLoggingInterceptor(httpBuilder: OkHttpClient.Builder) {
		val logging = HttpLoggingInterceptor()
		logging.level =
			if (true) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
		httpBuilder.addInterceptor(logging) // TODO BuildConfig.DEBUG
	}

	@Provides
	@Singleton
	fun preferences(
		@ApplicationContext
		context: Context,
	): Preferences {
		return PreferencesImpl(
			preferences = context.getSharedPreferences("mobile-preferences", Context.MODE_PRIVATE),
		)
	}

}