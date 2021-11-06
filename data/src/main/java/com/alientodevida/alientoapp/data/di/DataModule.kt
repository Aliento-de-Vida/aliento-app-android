package com.alientodevida.alientoapp.data.di

import android.content.Context
import com.alientodevida.alientoapp.data.preferences.PreferencesImpl
import com.alientodevida.alientoapp.data.storage.AppDatabase
import com.alientodevida.alientoapp.data.storage.RoomDao
import com.alientodevida.alientoapp.data.storage.getDatabase
import com.alientodevida.alientoapp.domain.preferences.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
	val contentType = "application/json".toMediaType()

	@Provides
	@Singleton
	fun json(): Json = Json { ignoreUnknownKeys = true }

	@Provides
	@Singleton
	@Named("Client")
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
	fun preferences(
		@ApplicationContext
		context: Context,
	): Preferences {
		return PreferencesImpl(
			preferences = context.getSharedPreferences("mobile-preferences", Context.MODE_PRIVATE),
		)
	}

	@Singleton
	@Provides
	fun providesDatabase(@ApplicationContext context: Context): AppDatabase = getDatabase(context)

	@Singleton
	@Provides
	fun videoDAO(database: AppDatabase): RoomDao = database.roomDao

}
