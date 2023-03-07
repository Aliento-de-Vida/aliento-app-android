package com.alientodevida.alientoapp.core.analytics

import androidx.compose.runtime.staticCompositionLocalOf

val LocalAnalyticsHelper = staticCompositionLocalOf<Analytics> {
    NoOpAnalytics()
}