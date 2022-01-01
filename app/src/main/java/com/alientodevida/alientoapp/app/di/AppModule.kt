package com.alientodevida.alientoapp.app.di

import com.alientodevida.alientoapp.app.logger.LoggerImpl
import com.alientodevida.alientoapp.domain.logger.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
  
  @Provides
  @Singleton
  fun logger(): Logger = LoggerImpl()
  
}
