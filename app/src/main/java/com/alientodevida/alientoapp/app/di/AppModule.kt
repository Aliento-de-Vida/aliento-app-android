package com.alientodevida.alientoapp.app.di

import com.alientodevida.alientoapp.common.logger.Logger
import com.google.firebase.messaging.FirebaseMessaging
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
  fun logger(): Logger = Logger()
  
  @Provides
  @Singleton
  fun firebaseMessaging(): FirebaseMessaging = FirebaseMessaging.getInstance()

}
