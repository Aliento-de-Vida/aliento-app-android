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
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
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

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
	private val contentType = "application/json".toMediaType()

	@Provides
	@Singleton
	fun json(): Json = Json { ignoreUnknownKeys = true }

	@Provides
	@Singleton
	fun okHttpClient(): OkHttpClient =
		OkHttpClient.Builder()
			.connectTimeout(15, TimeUnit.SECONDS)
			.writeTimeout(15, TimeUnit.SECONDS)
			.readTimeout(15, TimeUnit.SECONDS)
			.retryOnConnectionFailure(false)
			.apply {
				if (true /*BuildConfig.DEBUG*/) addInterceptor(
					HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
				)
			}.build()

	@Provides
	@Singleton
	fun api(
		client: OkHttpClient,
		json: Json,
	): RetrofitService = Retrofit.Builder()
		.client(client)
		.baseUrl(BASE_URL_SPOTIFY_API)
		.addConverterFactory(json.asConverterFactory(contentType))
		.build()
		.create(RetrofitService::class.java)
}