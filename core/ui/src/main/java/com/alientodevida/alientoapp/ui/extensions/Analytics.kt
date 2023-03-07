package com.alientodevida.alientoapp.ui.extensions

import com.alientodevida.alientoapp.core.analytics.Analytics
import com.alientodevida.alientoapp.core.analytics.AnalyticsEvent

fun Analytics.logScreenView(screenName: String) {
    logEvent(
        AnalyticsEvent(
            type = AnalyticsEvent.Types.SCREEN_VIEW,
            extras = listOf(
                AnalyticsEvent.Param(AnalyticsEvent.ParamKeys.SCREEN_NAME, screenName),
            ),
        ),
    )
}

// TODO move to feature
fun Analytics.logAdminLogin() {
    logEvent(
        AnalyticsEvent(
            type = "admin_login",
            extras = emptyList(),
        ),
    )
}

