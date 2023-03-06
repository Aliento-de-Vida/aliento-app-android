package com.alientodevida.alientoapp.domain.analytics

import com.alientodevida.alientoapp.domain.BuildConfig

interface Analytics {
    fun logEvent(event: AnalyticsEvent)
    fun logScreen(screen: AnalyticsScreen)
}

abstract class AnalyticsEvent {
    abstract val name: String
    val environment: String = BuildConfig.BUILD_TYPE
    open val params: Map<String, String> = emptyMap()

}

abstract class AnalyticsScreen {
    abstract val name: String
    val environment: String = BuildConfig.BUILD_TYPE
    open val params: Map<String, String> = emptyMap()
}