package com.alientodevida.alientoapp.app.features.sermons.video

import android.os.Bundle
import android.view.View
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.viewModels
import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.app.base.BaseFragment
import com.alientodevida.alientoapp.app.compose.theme.AppTheme
import com.alientodevida.alientoapp.app.databinding.FragmentNotificationsBinding
import com.alientodevida.alientoapp.app.utils.Utils
import com.alientodevida.alientoapp.app.utils.extensions.openYoutubeChannel
import com.alientodevida.alientoapp.domain.video.YoutubeVideo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VideoFragment : BaseFragment<FragmentNotificationsBinding>(R.layout.fragment_notifications) {
  
  private val viewModel by viewModels<VideoViewModel>()
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
  
    binding.composeView.apply {
      setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
      setContent {
        AppTheme {
          VideoSermons(
            viewModel = viewModel,
            onBackPressed = { activity?.onBackPressed() },
            goToYoutubePage = { goToYoutubePage()},
            goToYoutubeVideo = { item -> goToYoutubeVideo(item) },
          )
        }
      }
    }
  }
  
  private fun goToYoutubePage() {
    viewModel.viewModelState.value.home?.socialMedia?.youtubeChannelUrl?.let {
      requireActivity().openYoutubeChannel(it)
    }
  }
  
  private fun goToYoutubeVideo(item: YoutubeVideo) {
    Utils.handleOnClick(requireActivity(), item.id)
  }
  
}