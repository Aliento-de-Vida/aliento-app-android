package com.alientodevida.alientoapp.app.features.campus

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.app.base.BaseFragment
import com.alientodevida.alientoapp.app.databinding.FragmentAudioBinding
import com.alientodevida.alientoapp.app.databinding.FragmentCampusBinding
import com.alientodevida.alientoapp.app.databinding.ItemCampusBinding
import com.alientodevida.alientoapp.app.recyclerview.BaseDiffAdapter
import com.alientodevida.alientoapp.app.recyclerview.BaseViewHolder
import com.alientodevida.alientoapp.app.utils.extensions.openSpotifyArtistPage
import com.alientodevida.alientoapp.app.utils.extensions.openSpotifyWith
import com.alientodevida.alientoapp.domain.campus.Campus
import com.alientodevida.alientoapp.domain.entities.local.PodcastEntity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CampusFragment : BaseFragment<FragmentCampusBinding>(R.layout.fragment_campus) {

    private val viewModel by viewModels<CampusViewModel>()

    private val campusAdapter = BaseDiffAdapter(campusDiffCallback)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        observeViewModel()
    }

    private fun setupUI() {
        setupRecyclerView()
    }

    private fun observeViewModel() {
        viewModel.campus.observe(viewLifecycleOwner) {
            viewModelResult(it, binding.progressBar) {
                campusAdapter.submitList(it)
            }
        }
    }

    private fun setupRecyclerView() {
        val resourceListener = BaseViewHolder.Listener { resource: Campus, _ -> }
        campusAdapter.register<Campus, ItemCampusBinding, CampusViewHolder>(
            R.layout.item_campus,
            resourceListener,
        )

        binding.rvCampus.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.rvCampus.adapter = campusAdapter
    }

    private fun handleOnClick(audio: PodcastEntity) {
        requireActivity().openSpotifyWith(Uri.parse(audio.uri))
    }
}