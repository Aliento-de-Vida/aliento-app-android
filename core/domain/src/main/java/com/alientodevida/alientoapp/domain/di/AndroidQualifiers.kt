package com.alientodevida.alientoapp.domain.di

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class BaseUrl

@Qualifier
@Retention(RUNTIME)
annotation class ActivityIntent
