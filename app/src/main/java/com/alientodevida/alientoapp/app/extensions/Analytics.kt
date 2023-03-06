package com.alientodevida.alientoapp.app.extensions

import com.alientodevida.alientoapp.domain.analytics.Analytics
import com.alientodevida.alientoapp.domain.analytics.AnalyticsEvent

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

fun Analytics.logAdminLogin() {
    logEvent(
        AnalyticsEvent(
            type = "admin_login",
            extras = emptyList(),
        ),
    )
}

