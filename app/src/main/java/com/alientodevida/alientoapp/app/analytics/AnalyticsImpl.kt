package com.alientodevida.alientoapp.app.analytics

import android.os.Bundle
import com.alientodevida.alientoapp.domain.analytics.Analytics
import com.alientodevida.alientoapp.domain.analytics.AnalyticsEvent
import com.alientodevida.alientoapp.domain.analytics.AnalyticsScreen
import com.google.firebase.analytics.FirebaseAnalytics
import javax.inject.Inject

class AnalyticsImpl @Inject constructor(
    private val firebaseAnalytics: FirebaseAnalytics,
): Analytics {

    override fun logEvent(event: AnalyticsEvent) {
        firebaseAnalytics.logEvent(event.name, event.toBundle())
    }

    override fun logScreen(screen: AnalyticsScreen) {
        firebaseAnalytics.logEvent(screen.name, screen.toBundle())
    }

}

fun AnalyticsEvent.toBundle() = Bundle().apply {
    putString("event_name", name)
    putString("environment", environment)
    params.forEach { putString(it.key, it.value) }
}

fun AnalyticsScreen.toBundle() = Bundle().apply {
    putString("screen_name", name)
    putString("environment", environment)
    params.forEach { putString(it.key, it.value) }
}