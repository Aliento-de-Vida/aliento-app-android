package com.alientodevida.alientoapp.app.features.church

import android.os.Bundle
import android.view.View
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.viewModels
import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.app.base.BaseFragment
import com.alientodevida.alientoapp.app.compose.theme.AppTheme
import com.alientodevida.alientoapp.app.databinding.FragmentNotificationsBinding
import com.alientodevida.alientoapp.app.utils.Constants
import com.alientodevida.alientoapp.app.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChurchFragment : BaseFragment<FragmentNotificationsBinding>(R.layout.fragment_notifications) {
  
  private val viewModel by viewModels<ChurchViewModel>()
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
  
    binding.composeView.apply {
      setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
      setContent {
        AppTheme {
          Church(
            viewModel = viewModel,
            openVideo = { goToUsVideo() },
            goToVideo = { id: String -> goToVideo(id) },
            onBackPressed = { activity?.onBackPressed() },
          )
        }
      }
    }
  }
  
  private fun goToVideo(id: String) {
    Utils.handleOnClick(requireActivity(), id)
  }
  
  private fun goToUsVideo() {
    Utils.handleOnClick(requireActivity(), Constants.US_VIDEO)
  }
}