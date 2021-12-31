package com.alientodevida.alientoapp.app

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.alientodevida.alientoapp.app.databinding.ActivityMainBinding
import com.alientodevida.alientoapp.domain.preferences.Preferences
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
  
  private lateinit var binding: ActivityMainBinding
  
  @Inject
  lateinit var preferences: Preferences
  
  override fun onCreate(savedInstanceState: Bundle?) {
    setTheme(R.style.AppTheme)
    super.onCreate(savedInstanceState)
  
    FirebaseApp.initializeApp(this)
    FirebaseMessaging.getInstance().subscribeToTopic("push_notifications")
  
    window.setFlags(
      WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
      WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
    )
    
    binding = ActivityMainBinding.inflate(layoutInflater)
    
    observe()
    
    setContentView(binding.root)
  }
  
  private fun observe() {
    preferences.nightModeLive.observe(this) { nightMode ->
      nightMode?.let { delegate.localNightMode = it }
    }
  }
}