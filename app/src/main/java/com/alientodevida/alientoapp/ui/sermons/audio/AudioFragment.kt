package com.alientodevida.alientoapp.ui.sermons.audio

import android.net.Uri
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
import com.alientodevida.alientoapp.data.entities.local.PodcastEntity
import com.alientodevida.alientoapp.data.entities.network.base.ApiResult
import com.alientodevida.alientoapp.databinding.FragmentAudioBinding
import com.alientodevida.alientoapp.utils.Constants
import com.alientodevida.alientoapp.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class AudioFragment : Fragment() {

    private val viewModel by viewModels<AudioViewModel>()

    private lateinit var mAdapter: AudioRecyclerViewAdapter
    private lateinit var mLayoutManager: RecyclerView.LayoutManager

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val binding = FragmentAudioBinding.inflate(layoutInflater)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        setupUI(binding)
        observeViewModel(binding)

        return binding.root
    }

    private fun setupUI(binding: FragmentAudioBinding) {
        with(binding) {
            setupRecyclerView(myRecyclerView)

            swiperefresh.setOnRefreshListener { this@AudioFragment.viewModel.refreshContent(false) }

            spotifyFragmentAudios.setOnClickListener {
                Utils.openSpotifyArtistPage(requireContext(), Constants.SPOTIFY_ARTIST_ID)
            }
        }
    }

    private fun observeViewModel(binding: FragmentAudioBinding) {
        viewModel.podcasts.observe(owner = viewLifecycleOwner) {
            if (it.count() == 0) {
                viewModel.refreshContent(false)
            }

            mAdapter.audios = ArrayList(it)
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

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        mLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = mLayoutManager
        mAdapter = AudioRecyclerViewAdapter(requireContext(), ArrayList(), object : AudioRecyclerViewAdapter.ItemClickListener {
            override fun onItemClick(item: PodcastEntity) {
                handleOnClick(item)
            }
        })
        recyclerView.adapter = mAdapter
    }

    private fun handleOnClick(audio: PodcastEntity) {
        Utils.openSpotifyWith(requireContext(), Uri.parse(audio.uri))
    }
}