package com.alientodevida.alientoapp.app.features.settings

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.app.base.BaseFragment
import com.alientodevida.alientoapp.app.databinding.FragmentSettingsBinding
import com.alientodevida.alientoapp.domain.preferences.Preferences
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding>(R.layout.fragment_settings) {
  
  @Inject
  lateinit var preferences: Preferences
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    
    binding.lifecycleOwner = viewLifecycleOwner
    
    setupUI()
    observe()
  }
  
  private fun setupUI() { with(binding) {
    toolbarView.icBack.setOnClickListener { activity?.onBackPressed() }
    
    preferences.isDarkThemeLive.observe(viewLifecycleOwner) { isDarkTheme ->
      isDarkTheme?.let { swTheme.isChecked = it }
    }
  
    swTheme.setOnCheckedChangeListener { _, checked ->
      preferences.isDarkTheme = checked
    }
    
    swPushNotifications.setOnCheckedChangeListener { _, isChecked -> }
    
  }}
  
  private fun observe() {
    preferences.nightModeLive.observe(viewLifecycleOwner) { nightMode ->
      nightMode?.let {
        (activity as AppCompatActivity).delegate.localNightMode = it
      }
    }
  }
}

























