package com.alientodevida.alientoapp.app.features.sermons.audio

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.viewModels
import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.app.base.BaseFragment
import com.alientodevida.alientoapp.app.compose.theme.AppTheme
import com.alientodevida.alientoapp.app.databinding.FragmentNotificationsBinding
import com.alientodevida.alientoapp.app.utils.extensions.openSpotifyArtistPage
import com.alientodevida.alientoapp.app.utils.extensions.openSpotifyWith
import com.alientodevida.alientoapp.domain.audio.Audio
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AudioFragment : BaseFragment<FragmentNotificationsBinding>(R.layout.fragment_notifications) {
  
  private val viewModel by viewModels<AudioViewModel>()
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
  
    binding.composeView.apply {
      setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
      setContent {
        AppTheme {
          AudioSermons(
            viewModel = viewModel,
            onBackPressed = { activity?.onBackPressed() },
            goToSpotifyPage = { goToSpotifyPage()},
            goToSpotifyAudio = { item -> goToSpotifyAudio(item) },
          )
        }
      }
    }
  }
  
  private fun goToSpotifyPage() {
    viewModel.viewModelState.value.home?.socialMedia?.spotifyArtistId?.let {
      requireActivity().openSpotifyArtistPage(it)
    }
  }
  
  private fun goToSpotifyAudio(audio: Audio) {
    requireActivity().openSpotifyWith(Uri.parse(audio.uri))
  }
  
}