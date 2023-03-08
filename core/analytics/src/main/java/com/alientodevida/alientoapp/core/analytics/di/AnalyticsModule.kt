package com.alientodevida.alientoapp.core.analytics.di

import android.content.Context
import com.alientodevida.alientoapp.core.analytics.Analytics
import com.alientodevida.alientoapp.core.analytics.FirebaseAnalyticsHelper
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AnalyticsModule {

    @Provides
    @Singleton
    fun firebaseAnalytics(
        @ApplicationContext context: Context
    ): FirebaseAnalytics = FirebaseAnalytics.getInstance(context)

    @Provides
    @Singleton
    fun analytics(
        firebaseAnalytics: FirebaseAnalytics
    ): Analytics = FirebaseAnalyticsHelper(firebaseAnalytics)

}