package com.alientodevida.alientoapp.ui.di

import com.alientodevida.alientoapp.ui.utils.Utils
import com.alientodevida.alientoapp.ui.utils.UtilsEntryPoint
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PresentationBindsModule {

    @Binds
    abstract fun utils(
        utilsEntryPoint: UtilsEntryPoint
    ): Utils
}
