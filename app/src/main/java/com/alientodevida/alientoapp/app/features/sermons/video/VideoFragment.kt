package com.alientodevida.alientoapp.app.features.sermons.video

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.app.base.BaseFragment
import com.alientodevida.alientoapp.app.databinding.FragmentVideoBinding
import com.alientodevida.alientoapp.app.utils.Utils
import com.alientodevida.alientoapp.app.utils.extensions.openYoutubeChannel
import com.alientodevida.alientoapp.domain.video.YoutubeVideo
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class VideoFragment : BaseFragment<FragmentVideoBinding>(R.layout.fragment_video) {
  
  private val viewModel by viewModels<VideoViewModel>()
  
  private lateinit var mAdapter: VideoRecyclerViewAdapter
  private lateinit var mLayoutManager: RecyclerView.LayoutManager
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    
    setupUI(binding)
    observeViewModel(binding)
  }
  
  private fun observeViewModel(binding: FragmentVideoBinding) {
    viewModel.videos.observe(viewLifecycleOwner) { result ->
      viewModelResult(
        result,
        binding.progressBar
      ) { items ->
        binding.swiperefresh.isRefreshing = false
        
        if (items.count() == 0) {
          viewModel.refreshContent()
        }
        
        mAdapter.videos = items.filter { it.thumbnilsUrl != null }
        mAdapter.notifyDataSetChanged()
      }
    }
  }
  
  
  private fun setupUI(binding: FragmentVideoBinding) {
    with(binding) {
      swiperefresh.setOnRefreshListener { this@VideoFragment.viewModel.refreshContent() }
      
      setupRecyclerView(myRecyclerView)
      
      youtubeFragmentVideos.setOnClickListener {
        viewModel.home?.socialMedia?.youtubeChannelUrl?.let {
          requireActivity().openYoutubeChannel(it)
        }
      }
    }
  }
  
  private fun setupRecyclerView(recyclerView: RecyclerView) {
    recyclerView.setHasFixedSize(true)
    mLayoutManager = LinearLayoutManager(context)
    recyclerView.layoutManager = mLayoutManager
    mAdapter = VideoRecyclerViewAdapter(ArrayList(), object :
      VideoRecyclerViewAdapter.ItemClickListenerYoutube {
      override fun onItemClick(item: YoutubeVideo) {
        Utils.handleOnClick(requireActivity(), item.id)
      }
    })
    recyclerView.adapter = mAdapter
  }
}