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
import com.alientodevida.alientoapp.app.utils.Constants
import com.alientodevida.alientoapp.app.utils.Utils
import com.alientodevida.alientoapp.domain.entities.local.PodcastEntity
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

            swiperefresh.setOnRefreshListener { this@AudioFragment.viewModel.refreshContent(false) }

            spotifyFragmentAudios.setOnClickListener {
                Utils.openSpotifyArtistPage(requireContext(), Constants.SPOTIFY_ARTIST_ID)
            }
        }
    }

    private fun observeViewModel(binding: FragmentAudioBinding) {
        viewModel.podcasts.observe(viewLifecycleOwner) {
            if (it.count() == 0) {
                viewModel.refreshContent(false)
            }

            mAdapter.audios = ArrayList(it)
            mAdapter.notifyDataSetChanged()
            binding.swiperefresh.isRefreshing = false
        }

        viewModel.isGettingData.observe(viewLifecycleOwner) { isGettingData ->
            if (isGettingData.not()) binding.swiperefresh.isRefreshing = false
        }

    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        mLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = mLayoutManager
        mAdapter = AudioRecyclerViewAdapter(
            requireContext(),
            ArrayList(),
            object : AudioRecyclerViewAdapter.ItemClickListener {
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