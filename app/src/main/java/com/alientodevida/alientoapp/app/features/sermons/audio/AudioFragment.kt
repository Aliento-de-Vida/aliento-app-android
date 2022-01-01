package com.alientodevida.alientoapp.app.features.sermons.audio

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.app.base.BaseFragment
import com.alientodevida.alientoapp.app.databinding.FragmentAudioBinding
import com.alientodevida.alientoapp.app.utils.extensions.openSpotifyArtistPage
import com.alientodevida.alientoapp.app.utils.extensions.openSpotifyWith
import com.alientodevida.alientoapp.domain.entities.local.Audio
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AudioFragment : BaseFragment<FragmentAudioBinding>(R.layout.fragment_audio) {
  
  private val viewModel by viewModels<AudioViewModel>()
  
  private lateinit var mAdapter: AudioRecyclerViewAdapter
  private lateinit var mLayoutManager: RecyclerView.LayoutManager
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    
    setupUI(binding)
    observeViewModel(binding)
  }
  
  private fun setupUI(binding: FragmentAudioBinding) {
    with(binding) {
      setupRecyclerView(myRecyclerView)
      
      swiperefresh.setOnRefreshListener { this@AudioFragment.viewModel.refreshContent() }
      
      spotifyFragmentAudios.setOnClickListener {
        viewModel.home?.socialMedia?.spotifyArtistId?.let {
          requireActivity().openSpotifyArtistPage(it)
        }
      }
    }
  }
  
  private fun observeViewModel(binding: FragmentAudioBinding) {
    viewModel.podcasts.observe(viewLifecycleOwner) {
      viewModelResult(it, binding.progressBar) {
        binding.swiperefresh.isRefreshing = false
        
        if (it.count() == 0) viewModel.refreshContent()
        
        mAdapter.audio = ArrayList(it)
        mAdapter.notifyDataSetChanged()
      }
    }
  }
  
  private fun setupRecyclerView(recyclerView: RecyclerView) {
    mLayoutManager = LinearLayoutManager(context)
    recyclerView.layoutManager = mLayoutManager
    mAdapter = AudioRecyclerViewAdapter(
      requireContext(),
      ArrayList(),
      object : AudioRecyclerViewAdapter.ItemClickListener {
        override fun onItemClick(item: Audio) {
          handleOnClick(item)
        }
      })
    recyclerView.adapter = mAdapter
  }
  
  private fun handleOnClick(audio: Audio) {
    requireActivity().openSpotifyWith(Uri.parse(audio.uri))
  }
}