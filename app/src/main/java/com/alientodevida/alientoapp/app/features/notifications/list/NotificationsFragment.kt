package com.alientodevida.alientoapp.app.features.notifications.list

import android.os.Bundle
import android.view.View
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.app.base.BaseFragment
import com.alientodevida.alientoapp.app.compose.theme.AppTheme
import com.alientodevida.alientoapp.app.databinding.FragmentNotificationsBinding
import com.alientodevida.alientoapp.domain.notification.Notification
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationsFragment :
  BaseFragment<FragmentNotificationsBinding>(R.layout.fragment_notifications) {
  
  private val viewModel by viewModels<NotificationsViewModel>()
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
  
    binding.composeView.apply {
      setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
      setContent {
        AppTheme {
          Notifications(
            viewModel = viewModel,
            onBackPressed = { activity?.onBackPressed() },
            goToEditNotification = { notification -> goToEditNotification(notification)},
            goToCreateNotification = { goToCreateNotification() },
          )
        }
      }
    }
  }
  
  private fun goToNotificationDetail(notification: Notification) {
    findNavController().navigate(
      NotificationsFragmentDirections.actionFragmentNotificationsToFragmentNotificationDetail(
        notification
      )
    )
  }
  
  private fun goToEditNotification(notification: Notification) {
    findNavController().navigate(
      NotificationsFragmentDirections.actionFragmentNotificationsToEditCreateNotificationFragment(
        notification
      )
    )
  }
  
  private fun goToCreateNotification() {
    findNavController().navigate(
      NotificationsFragmentDirections.actionFragmentNotificationsToEditCreateNotificationFragment(null)
    )
  }
  
}