package com.alientodevida.alientoapp.app.features.campus.editcreate

import android.os.Bundle
import android.view.View
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.viewModels
import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.app.base.BaseFragment
import com.alientodevida.alientoapp.app.compose.theme.AppTheme
import com.alientodevida.alientoapp.app.databinding.FragmentNotificationsBinding
import com.alientodevida.alientoapp.domain.campus.Campus
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditCreateCampusFragment :
  BaseFragment<FragmentNotificationsBinding>(R.layout.fragment_notifications) {
  
  private val viewModel by viewModels<EditCreateNotificationViewModel>()
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
  
    binding.composeView.apply {
      setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
      setContent {
        AppTheme {
          EditCreateCampus(
            viewModel = viewModel,
            campus = Campus.empty(),
            onBackPressed = { activity?.onBackPressed() },
          )
        }
      }
    }
  }
  
}