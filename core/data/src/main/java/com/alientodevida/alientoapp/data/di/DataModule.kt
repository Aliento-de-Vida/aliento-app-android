package com.alientodevida.alientoapp.data.di

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import com.alientodevida.alientoapp.core.data.BuildConfig
import com.alientodevida.alientoapp.data.network.AdminAuthenticator
import com.alientodevida.alientoapp.data.network.AdminAuthenticatorInterceptor
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
                if (BuildConfig.DEBUG) {
                    addInterceptor(
                        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC),
                    )
                }
            }.build()

    @Provides
    @Singleton
    @Named("AdminAuthClient")
    fun okHttpAdminClient(
        authInterceptor: AdminAuthenticatorInterceptor,
        authenticator: AdminAuthenticator,
    ): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)
            .apply {
                addInterceptor(authInterceptor)
                authenticator(authenticator)
                if (BuildConfig.DEBUG) {
                    addInterceptor(
                        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC),
                    )
                }
            }.build()

    @Provides
    @Singleton
    fun preferences(
        @ApplicationContext
        context: Context,
    ): Preferences {
        val preferences: SharedPreferences = context.sharedPreferences()
        val preferencesStore: DataStore<androidx.datastore.preferences.core.Preferences> =
            context.preferencesStore

        return com.alientodevida.alientoapp.data.preferences.PreferencesImpl(
            preferences = preferences,
            preferencesStore = preferencesStore,
        )
    }
}

private fun Context.sharedPreferences(): SharedPreferences =
    getSharedPreferences("mobile-preferences", Context.MODE_PRIVATE)

private val Context.preferencesStore: DataStore<androidx.datastore.preferences.core.Preferences> by preferencesDataStore(
    name = "preferences",
)
