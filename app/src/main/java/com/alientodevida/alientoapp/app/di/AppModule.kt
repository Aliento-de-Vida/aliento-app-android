package com.alientodevida.alientoapp.app.di

import com.alientodevida.alientoapp.app.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Named("base-url")
    fun baseUrl(): String = BuildConfig.BASE_URL

    @Provides
    @Named("youtube-key")
    fun youtubeKey(): String = BuildConfig.YOUTUBE_DEVELOPER_KEY
}