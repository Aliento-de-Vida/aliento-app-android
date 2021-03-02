package com.alientodevida.alientoapp.ui.sermons.video

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alientodevida.alientoapp.data.entities.local.YoutubePlaylistItemEntity
import com.alientodevida.alientoapp.databinding.FragmentVideoBinding
import com.alientodevida.alientoapp.utils.Constants
import com.alientodevida.alientoapp.utils.Utils
import com.google.android.youtube.player.YouTubeStandalonePlayer
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
    ): View? {
        val binding = FragmentVideoBinding.inflate(layoutInflater)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        setupUI(binding)

        return binding.root
    }


    private fun setupUI(binding: FragmentVideoBinding) {

        binding.swiperefresh.setOnRefreshListener { viewModel.refreshContent() }

        setupRecyclerView(binding.myRecyclerView)

        binding.youtubeFragmentVideos.setOnClickListener{
            Utils.openYoutubeChannel(requireContext(), Constants.YOUTUBE_CHANNEL_URL)
        }

        viewModel.videos.observe(viewLifecycleOwner) {
            if (it.count() == 0) {
                viewModel.refreshContent()
            }

            mAdapter.videos = it
            mAdapter.notifyDataSetChanged()
            binding.swiperefresh.isRefreshing = false
        }
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = mLayoutManager
        mAdapter = VideoRecyclerViewAdapter(ArrayList() , object :
            VideoRecyclerViewAdapter.ItemClickListenerYoutube {
            override fun onItemClick(item: YoutubePlaylistItemEntity) {
                Utils.handleOnClick(requireActivity(), item.id)
            }
        })
        recyclerView.adapter = mAdapter
    }
}