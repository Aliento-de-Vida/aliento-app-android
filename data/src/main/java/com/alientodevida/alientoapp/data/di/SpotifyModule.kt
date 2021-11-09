package com.alientodevida.alientoapp.data.di

import com.alientodevida.alientoapp.data.spotify.*
import com.alientodevida.alientoapp.data.storage.RoomDao
import com.alientodevida.alientoapp.domain.spotify.SpotifyRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
                if (true /*BuildConfig.DEBUG*/) addInterceptor(
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                )
            }.build()

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
        roomDao: RoomDao
    ): SpotifyRepository = SpotifyRepositoryImpl(spotifyApi, roomDao)

}