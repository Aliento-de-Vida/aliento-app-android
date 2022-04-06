package com.alientodevida.alientoapp.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.alientodevida.alientoapp.app.databinding.ActivityMainBinding
import com.alientodevida.alientoapp.domain.preferences.Preferences
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
  
  private lateinit var binding: ActivityMainBinding
  
  @Inject
  lateinit var preferences: Preferences
  
  @Inject
  lateinit var firebaseMessaging: FirebaseMessaging
  
  override fun onCreate(savedInstanceState: Bundle?) {
    setTheme(R.style.AppTheme)
    super.onCreate(savedInstanceState)
    
    setupPushNotifications()
  
    binding = ActivityMainBinding.inflate(layoutInflater)
    
    observe()
    
    setContentView(binding.root)
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