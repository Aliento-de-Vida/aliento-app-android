package com.alientodevida.alientoapp.sermons.di

import com.alientodevida.alientoapp.data.di.DataModule
import com.alientodevida.alientoapp.sermons.BuildConfig
import com.alientodevida.alientoapp.sermons.data.spotify.*
import com.alientodevida.alientoapp.sermons.data.storage.RoomDao
import com.alientodevida.alientoapp.sermons.domain.audio.SpotifyRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SpotifyModule {

    @Provides
    @Singleton
    @Named("SpotifyAuthClient")
    fun okHttpClient(
        authInterceptor: SpotifyAuthenticatorInterceptor,
        authenticator: SpotifyAuthenticator,
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

    @OptIn(ExperimentalSerializationApi::class)
    @Singleton
    @Provides
    fun spotifyApi(
        @Named("SpotifyAuthClient")
        okHttpClient: OkHttpClient,
        json: Json,
    ): SpotifyApi = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BASE_URL_SPOTIFY_API)
        .addConverterFactory(json.asConverterFactory(DataModule.contentType))
        .build()
        .create(SpotifyApi::class.java)

    const val BASE_URL_SPOTIFY_API = "https://api.spotify.com/"

    @OptIn(ExperimentalSerializationApi::class)
    @Singleton
    @Provides
    fun spotifyAuthApi(
        @Named("Client")
        okHttpClient: OkHttpClient,
        json: Json,
    ): SpotifyAuthApi = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("https://accounts.spotify.com")
        .addConverterFactory(json.asConverterFactory(DataModule.contentType))
        .build()
        .create(SpotifyAuthApi::class.java)

    @Singleton
    @Provides
    fun spotifyRepository(
        spotifyApi: SpotifyApi,
        roomDao: RoomDao,
    ): SpotifyRepository =
        SpotifyRepositoryImpl(spotifyApi, roomDao)
}
