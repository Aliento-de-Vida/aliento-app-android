package com.alientodevida.alientoapp.core.analytics

class NoOpAnalytics : Analytics {
    override fun logEvent(event: AnalyticsEvent) = Unit
}
