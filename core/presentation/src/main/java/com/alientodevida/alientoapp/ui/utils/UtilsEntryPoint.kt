package com.alientodevida.alientoapp.ui.utils

import androidx.compose.runtime.staticCompositionLocalOf
import com.alientodevida.alientoapp.core.analytics.Analytics
import com.alientodevida.alientoapp.core.analytics.NoOpAnalytics
import com.alientodevida.alientoapp.domain.di.YoutubeKey
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

interface Utils {
    val youtubeKey: String
    val analytics: Analytics
}

@Singleton
class UtilsEntryPoint @Inject constructor(
    @YoutubeKey override val youtubeKey: String,
    override val analytics: Analytics,
) : Utils

class NoOpUtils : Utils {
    override val youtubeKey: String = ""
    override val analytics: Analytics = NoOpAnalytics()
}

val LocalUtils = staticCompositionLocalOf<Utils> {
    NoOpUtils()
}
