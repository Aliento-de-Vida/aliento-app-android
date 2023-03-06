package com.alientodevida.alientoapp.app.analytics

import com.alientodevida.alientoapp.domain.analytics.Analytics
import com.alientodevida.alientoapp.domain.analytics.AnalyticsEvent
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import javax.inject.Inject

class FirebaseAnalytics @Inject constructor(
    private val firebaseAnalytics: FirebaseAnalytics,
) : Analytics {

    override fun logEvent(event: AnalyticsEvent) {
        firebaseAnalytics.logEvent(event.type) {
            for (extra in event.extras) {
                // Truncate parameter keys and values according to firebase maximum length values.
                param(
                    key = extra.key.take(40),
                    value = extra.value.take(100),
                )
            }
        }
    }

}