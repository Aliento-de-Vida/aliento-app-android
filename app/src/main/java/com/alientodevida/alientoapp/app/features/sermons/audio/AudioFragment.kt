package com.alientodevida.alientoapp.app.features.sermons.audio

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.app.base.BaseFragment
import com.alientodevida.alientoapp.app.databinding.FragmentAudioBinding
import com.alientodevida.alientoapp.app.databinding.ItemAudioCardBinding
import com.alientodevida.alientoapp.app.recyclerview.BaseDiffAdapter
import com.alientodevida.alientoapp.app.recyclerview.BaseViewHolder
import com.alientodevida.alientoapp.app.utils.extensions.openSpotifyArtistPage
import com.alientodevida.alientoapp.app.utils.extensions.openSpotifyWith
import com.alientodevida.alientoapp.domain.audio.Audio
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AudioFragment : BaseFragment<FragmentAudioBinding>(R.layout.fragment_audio) {
  
  private val viewModel by viewModels<AudioViewModel>()
  
  private val audioAdapter = BaseDiffAdapter(audioDiffCallback)
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    
    setupUI()
    observeViewModel()
  }
  
  private fun setupUI() {
    with(binding) {
      swiperefresh.setOnRefreshListener { this@AudioFragment.viewModel.refreshContent() }
      
      setupRecyclerView()
      
      spotifyFragmentAudios.setOnClickListener {
        viewModel.home?.socialMedia?.spotifyArtistId?.let {
          requireActivity().openSpotifyArtistPage(it)
        }
      }
    }
  }
  
  private fun setupRecyclerView() {
    val resourceListener = BaseViewHolder.Listener { item: Audio, _ ->
      handleOnClick(item)
    }
    audioAdapter.register<Audio, ItemAudioCardBinding, AudioViewHolder>(
      R.layout.item_audio_card,
      resourceListener,
    )
  
    binding.myRecyclerView.layoutManager = LinearLayoutManager(
      requireContext(),
      LinearLayoutManager.VERTICAL,
      false
    )
    binding.myRecyclerView.adapter = audioAdapter
  }
  
  private fun handleOnClick(audio: Audio) {
    requireActivity().openSpotifyWith(Uri.parse(audio.uri))
  }
  
  private fun observeViewModel() {
    viewModel.podcasts.observe(viewLifecycleOwner) {
      viewModelResult(it, binding.progressBar) {
        binding.swiperefresh.isRefreshing = false
        
        if (it.count() == 0) viewModel.refreshContent()
        
        audioAdapter.submitList(it)
      }
    }
  }
  
}