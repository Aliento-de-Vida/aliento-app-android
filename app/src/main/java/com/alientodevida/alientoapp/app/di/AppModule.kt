package com.alientodevida.alientoapp.app.di

import android.content.Context
import com.alientodevida.alientoapp.app.logger.LoggerImpl
import com.alientodevida.alientoapp.domain.analytics.Analytics
import com.alientodevida.alientoapp.domain.logger.Logger
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.messaging.FirebaseMessaging
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.alientodevida.alientoapp.app.analytics.FirebaseAnalytics as AppFirebaseAnalytics
@Module
@InstallIn(SingletonComponent::class)
class AppModule {
  
  @Provides
  @Singleton
  fun logger(): Logger = LoggerImpl()
  
  @Provides
  @Singleton
  fun firebaseMessaging(): FirebaseMessaging = FirebaseMessaging.getInstance()

  @Provides
  @Singleton
  fun firebaseAnalytics(
    @ApplicationContext context: Context
  ): FirebaseAnalytics = FirebaseAnalytics.getInstance(context)

  @Provides
  @Singleton
  fun analytics(
    firebaseAnalytics: FirebaseAnalytics
  ): Analytics = AppFirebaseAnalytics(firebaseAnalytics)

}
