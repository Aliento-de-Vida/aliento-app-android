package com.alientodevida.alientoapp.domain.di

import com.alientodevida.alientoapp.domain.coroutines.DefaultDispatcher
import com.alientodevida.alientoapp.domain.coroutines.ImmediateDispatcher
import com.alientodevida.alientoapp.domain.coroutines.IoDispatcher
import com.alientodevida.alientoapp.domain.coroutines.MainDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

	@Provides
	@Singleton
	@DefaultDispatcher
	fun defaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

	@Provides
	@Singleton
	@IoDispatcher
	fun ioDispatcher(): CoroutineDispatcher = Dispatchers.IO

	@Provides
	@Singleton
	@MainDispatcher
	fun mainDispatcher(): CoroutineDispatcher = Dispatchers.Main

	@Provides
	@Singleton
	@ImmediateDispatcher
	fun immediateDispatcher(): CoroutineDispatcher = Dispatchers.Main.immediate

}
