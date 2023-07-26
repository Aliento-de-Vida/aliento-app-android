package com.alientodevida.alientoapp.app.di

import com.alientodevida.alientoapp.app.BuildConfig
import com.alientodevida.alientoapp.app.MainActivity
import com.alientodevida.alientoapp.domain.di.ActivityIntent
import com.alientodevida.alientoapp.domain.di.BaseUrl
import com.alientodevida.alientoapp.domain.di.SpotifyBasicToken
import com.alientodevida.alientoapp.domain.di.YoutubeKey
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @BaseUrl
    fun baseUrl(): String = BuildConfig.BASE_URL

    @Provides
    @ActivityIntent
    fun notificationClass(): Class<*> = MainActivity::class.java

    @Provides
    @YoutubeKey
    fun youtubeKey(): String = BuildConfig.YOUTUBE_DEVELOPER_KEY

    @Provides
    @SpotifyBasicToken
    fun spotifyBasicToken(): String = BuildConfig.SPOTIFY_BASIC_TOKEN
}
