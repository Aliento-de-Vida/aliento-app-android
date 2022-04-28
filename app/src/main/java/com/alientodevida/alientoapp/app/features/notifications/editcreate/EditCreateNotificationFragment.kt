package com.alientodevida.alientoapp.app.features.notifications.editcreate

import android.os.Bundle
import android.view.View
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.viewModels
import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.app.base.BaseFragment
import com.alientodevida.alientoapp.app.compose.theme.AppTheme
import com.alientodevida.alientoapp.app.databinding.FragmentNotificationsBinding
import com.alientodevida.alientoapp.domain.notification.Notification
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditCreateNotificationFragment :
  BaseFragment<FragmentNotificationsBinding>(R.layout.fragment_notifications) {
  
  private val viewModel by viewModels<EditCreateNotificationViewModel>()
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
  
    binding.composeView.apply {
      setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
      setContent {
        AppTheme {
          EditNotification(
            viewModel = viewModel,
            notification = Notification.empty(),
            onBackPressed = { activity?.onBackPressed() },
          )
        }
      }
    }
  }
  
}