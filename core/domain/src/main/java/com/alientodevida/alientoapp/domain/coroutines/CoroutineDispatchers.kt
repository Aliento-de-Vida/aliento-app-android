package com.alientodevida.alientoapp.domain.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Qualifier
import javax.inject.Singleton

@Singleton
class CoroutineDispatchers @Inject constructor(
  @DefaultDispatcher
  val default: CoroutineDispatcher,
  
  @IoDispatcher
  val io: CoroutineDispatcher,
  
  @MainDispatcher
  val main: CoroutineDispatcher,
  
  @ImmediateDispatcher
  val immediate: CoroutineDispatcher,
)

@Qualifier
annotation class DefaultDispatcher

@Qualifier
annotation class IoDispatcher

@Qualifier
annotation class MainDispatcher

@Qualifier
annotation class ImmediateDispatcher
