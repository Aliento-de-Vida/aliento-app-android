package com.alientodevida.alientoapp.ui.di

import com.alientodevida.alientoapp.domain.di.ActivityIntent
import com.google.firebase.messaging.FirebaseMessaging
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PresentationModule {

    @Provides
    @Singleton
    fun firebaseMessaging(): FirebaseMessaging = FirebaseMessaging.getInstance()

}
