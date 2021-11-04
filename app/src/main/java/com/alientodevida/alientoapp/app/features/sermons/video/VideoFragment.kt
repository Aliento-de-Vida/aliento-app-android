package com.alientodevida.alientoapp.app.features.sermons.video

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alientodevida.alientoapp.app.databinding.FragmentVideoBinding
import com.alientodevida.alientoapp.app.utils.Constants
import com.alientodevida.alientoapp.app.utils.Utils
import com.alientodevida.alientoapp.domain.entities.local.YoutubePlaylistItemEntity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class VideoFragment : Fragment() {

    private val viewModel by viewModels<VideoViewModel>()

    private lateinit var mAdapter: VideoRecyclerViewAdapter
    private lateinit var mLayoutManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentVideoBinding.inflate(layoutInflater)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        setupUI(binding)
        observeViewModel(binding)

        return binding.root
    }

    private fun observeViewModel(binding: FragmentVideoBinding) {
        viewModel.videos.observe(viewLifecycleOwner) { items ->
            if (items.count() == 0) {
                viewModel.refreshContent()
            }

            mAdapter.videos = items.filter { it.thumbnilsUrl != null }
            mAdapter.notifyDataSetChanged()
            binding.swiperefresh.isRefreshing = false
        }

        viewModel.isGettingData.observe(viewLifecycleOwner) { isGettingData ->
            if (isGettingData.not()) binding.swiperefresh.isRefreshing = false
        }

    }


    private fun setupUI(binding: FragmentVideoBinding) {
        with(binding) {
            swiperefresh.setOnRefreshListener { this@VideoFragment.viewModel.refreshContent() }

            setupRecyclerView(myRecyclerView)

            youtubeFragmentVideos.setOnClickListener {
                Utils.openYoutubeChannel(requireContext(), Constants.YOUTUBE_CHANNEL_URL)
            }
        }
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = mLayoutManager
        mAdapter = VideoRecyclerViewAdapter(ArrayList(), object :
            VideoRecyclerViewAdapter.ItemClickListenerYoutube {
            override fun onItemClick(item: YoutubePlaylistItemEntity) {
                Utils.handleOnClick(requireActivity(), item.id)
            }
        })
        recyclerView.adapter = mAdapter
    }
}