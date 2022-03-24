package com.alientodevida.alientoapp.app.features.sermons.video

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.app.base.BaseFragment
import com.alientodevida.alientoapp.app.databinding.FragmentVideoBinding
import com.alientodevida.alientoapp.app.databinding.ItemVideoCardBinding
import com.alientodevida.alientoapp.app.recyclerview.BaseDiffAdapter
import com.alientodevida.alientoapp.app.recyclerview.BaseViewHolder
import com.alientodevida.alientoapp.app.utils.Utils
import com.alientodevida.alientoapp.app.utils.extensions.openYoutubeChannel
import com.alientodevida.alientoapp.domain.video.YoutubeVideo
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class VideoFragment : BaseFragment<FragmentVideoBinding>(R.layout.fragment_video) {
  
  private val viewModel by viewModels<VideoViewModel>()
  
  private val videoAdapter = BaseDiffAdapter(videoDiffCallback)
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    
    setupUI()
    observeViewModel()
  }
  
  private fun setupUI() {
    with(binding) {
      swiperefresh.setOnRefreshListener { this@VideoFragment.viewModel.refreshContent() }
      
      setupRecyclerView()
      
      youtubeFragmentVideos.setOnClickListener {
        viewModel.home?.socialMedia?.youtubeChannelUrl?.let {
          requireActivity().openYoutubeChannel(it)
        }
      }
    }
  }
  
  private fun setupRecyclerView() {
    val resourceListener = BaseViewHolder.Listener { item: YoutubeVideo, _ ->
        Utils.handleOnClick(requireActivity(), item.id)
    }
    videoAdapter.register<YoutubeVideo, ItemVideoCardBinding, VideoViewHolder>(
      R.layout.item_video_card,
      resourceListener,
    )
  
    binding.myRecyclerView.layoutManager = LinearLayoutManager(
      requireContext(),
      LinearLayoutManager.VERTICAL,
      false
    )
    binding.myRecyclerView.adapter = videoAdapter
  }
  
  private fun observeViewModel() {
    viewModel.videos.observe(viewLifecycleOwner) { result ->
      viewModelResult(
        result,
        binding.progressBar
      ) { items ->
        binding.swiperefresh.isRefreshing = false
        
        if (items.count() == 0) {
          viewModel.refreshContent()
        }
        
        videoAdapter.submitList(items.filter { it.thumbnilsUrl != null })
      }
    }
  }
  
}