package com.alientodevida.alientoapp.ui.sermons.video

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alientodevida.alientoapp.data.entities.local.YoutubePlaylistItemEntity
import com.alientodevida.alientoapp.data.entities.network.base.ApiResult
import com.alientodevida.alientoapp.databinding.FragmentVideoBinding
import com.alientodevida.alientoapp.utils.Constants
import com.alientodevida.alientoapp.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception


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
        viewModel.videos.observe(owner = viewLifecycleOwner) { items ->
            if (items.count() == 0) {
                viewModel.refreshContent()
            }

            mAdapter.videos = items.filter { it.thumbnilsUrl != null }
            mAdapter.notifyDataSetChanged()
            binding.swiperefresh.isRefreshing = false
        }

        viewModel.isGettingData.observe(owner = viewLifecycleOwner) { isGettingData ->
            if (isGettingData.not()) binding.swiperefresh.isRefreshing = false
        }

        viewModel.onError.observe(owner = viewLifecycleOwner) { onError ->
            when(onError.result) {
                is ApiResult.ApiError -> Toast.makeText(requireContext(), "ApiError", Toast.LENGTH_SHORT).show()
                is ApiResult.NetworkError -> Toast.makeText(requireContext(), "NetworkError", Toast.LENGTH_SHORT).show()
                is ApiResult.UnknownError -> Toast.makeText(requireContext(), "UnknownError", Toast.LENGTH_SHORT).show()
                else -> throw Exception("This is not an error")
            }
        }
    }


    private fun setupUI(binding: FragmentVideoBinding) {
        with(binding) {
            swiperefresh.setOnRefreshListener { this@VideoFragment.viewModel.refreshContent() }

            setupRecyclerView(myRecyclerView)

            youtubeFragmentVideos.setOnClickListener{
                Utils.openYoutubeChannel(requireContext(), Constants.YOUTUBE_CHANNEL_URL)
            }
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