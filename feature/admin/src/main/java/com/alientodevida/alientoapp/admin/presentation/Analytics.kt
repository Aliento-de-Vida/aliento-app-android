package com.alientodevida.alientoapp.admin.presentation

import com.alientodevida.alientoapp.core.analytics.Analytics
import com.alientodevida.alientoapp.core.analytics.AnalyticsEvent

fun Analytics.logAdminLogin() {
    logEvent(
        AnalyticsEvent(
            type = "admin_login",
            extras = emptyList(),
        ),
    )
}
