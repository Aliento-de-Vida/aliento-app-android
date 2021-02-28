package com.alientodevida.alientoapp.ui.sermons.audio

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
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
import com.alientodevida.alientoapp.data.entities.local.PodcastEntity
import com.alientodevida.alientoapp.databinding.FragmentAudioBinding
import com.alientodevida.alientoapp.utils.Constants
import com.alientodevida.alientoapp.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AudioFragment : Fragment() {

    private val viewModel by viewModels<AudioViewModel>()

    private lateinit var mAdapter: AudioRecyclerViewAdapter
    private lateinit var mLayoutManager: RecyclerView.LayoutManager

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAudioBinding.inflate(layoutInflater)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        setupUI(binding)

        return binding.root
    }

    private fun setupUI(binding: FragmentAudioBinding) {

        setupRecyclerView(binding.myRecyclerView)

        binding.swiperefresh.setOnRefreshListener { viewModel.refreshContent() }

        viewModel.podcasts.observe(viewLifecycleOwner) {
            if (it.count() == 0) {
                viewModel.refreshContent()
            }

            mAdapter.audios = ArrayList(it)
            mAdapter.notifyDataSetChanged()
            binding.swiperefresh.isRefreshing = false
        }

        binding.spotifyFragmentAudios.setOnClickListener {
            Utils.openSpotifyArtistPage(requireContext(), Constants.SPOTIFY_ARTIST_ID)
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