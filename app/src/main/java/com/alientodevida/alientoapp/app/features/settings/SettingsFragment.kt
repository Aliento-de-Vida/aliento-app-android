package com.alientodevida.alientoapp.app.features.settings

import android.os.Bundle
import android.view.View
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.viewModels
import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.app.base.BaseFragment
import com.alientodevida.alientoapp.app.compose.theme.AppTheme
import com.alientodevida.alientoapp.app.databinding.FragmentNotificationsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentNotificationsBinding>(R.layout.fragment_notifications) {
  
  private val viewModel by viewModels<SettingsViewModel>()
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    
    binding.composeView.apply {
      setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
      setContent {
        AppTheme {
          EditCreateGallery(
            viewModel = viewModel,
            onBackPressed = { activity?.onBackPressed() },
          )
        }
      }
    }
  }
  
  /*private fun setupUI() {
    with(binding) {
      toolbarView.icBack.setOnClickListener { activity?.onBackPressed() }
      
      preferences.isDarkThemeLive.observe(viewLifecycleOwner) { isDarkTheme ->
        isDarkTheme?.let { swTheme.isChecked = it }
      }
      
      swTheme.setOnCheckedChangeListener { _, isChecked ->
        preferences.isDarkTheme = isChecked
      }
      
      swPushNotifications.isChecked = preferences.pushEnabled
      swPushNotifications.setOnCheckedChangeListener { _, isChecked ->
        preferences.pushEnabled = isChecked
        if (isChecked)
          firebaseMessaging.subscribeToTopic("push_notifications")
        else
          firebaseMessaging.unsubscribeFromTopic("push_notifications")
      }
      
    }
  }*/
}

























