package com.alientodevida.alientoapp.app.features.sermons

import android.os.Bundle
import android.view.View
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.app.base.BaseFragment
import com.alientodevida.alientoapp.app.compose.theme.AppTheme
import com.alientodevida.alientoapp.app.databinding.FragmentNotificationsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SermonsFragment : BaseFragment<FragmentNotificationsBinding>(R.layout.fragment_notifications) {
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    
    binding.composeView.apply {
      setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
      setContent {
        AppTheme { Sermons { activity?.onBackPressed() } }
      }
    }
  }
  
}