package com.alientodevida.alientoapp.app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.CompositionLocalProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.alientodevida.alientoapp.app.navigation.MainNavigationGraph
import com.alientodevida.alientoapp.core.analytics.Analytics
import com.alientodevida.alientoapp.core.analytics.LocalAnalyticsHelper
import com.alientodevida.alientoapp.domain.preferences.Preferences
import com.alientodevida.alientoapp.ui.theme.AppTheme
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
  
  @Inject
  lateinit var preferences: Preferences
  
  @Inject
  lateinit var firebaseMessaging: FirebaseMessaging

  @Inject
  lateinit var analytics: Analytics
  
  override fun onCreate(savedInstanceState: Bundle?) {
    setTheme(R.style.AppTheme)
    super.onCreate(savedInstanceState)
    
    setupPushNotifications()
    observe()

    setContent {
      CompositionLocalProvider(LocalAnalyticsHelper provides analytics) {
        AppTheme {
          MainNavigationGraph()
        }
      }
    }
  }
  
  private fun setupPushNotifications() {
    FirebaseApp.initializeApp(this)
    if (preferences.pushEnabled) firebaseMessaging.subscribeToTopic("push_notifications")
  }
  
  private fun observe() {
    lifecycleScope.launch {
      lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
        preferences.isDarkThemeFlow.collectLatest { isDark ->
          delegate.localNightMode = if (isDark) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        }
      }
    }
  }
}