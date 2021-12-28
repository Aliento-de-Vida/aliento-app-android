package com.alientodevida.alientoapp.app.features.campus.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.app.base.BaseFragment
import com.alientodevida.alientoapp.app.databinding.FragmentCampusesBinding
import com.alientodevida.alientoapp.app.databinding.ItemCampusBinding
import com.alientodevida.alientoapp.app.recyclerview.BaseDiffAdapter
import com.alientodevida.alientoapp.app.recyclerview.BaseViewHolder
import com.alientodevida.alientoapp.domain.campus.Campus
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CampusesFragment : BaseFragment<FragmentCampusesBinding>(R.layout.fragment_campuses) {

    private val viewModel by viewModels<CampusesViewModel>()

    private val campusAdapter = BaseDiffAdapter(campusDiffCallback)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        observeViewModel()
    }

    private fun setupUI() {
        binding.toolbarView.icBack.setOnClickListener { activity?.onBackPressed() }

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
        val resourceListener = BaseViewHolder.Listener { campus: Campus, _ ->
            findNavController().navigate(CampusesFragmentDirections.actionFragmentCampusToCampusDetailFragment(campus))
        }
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

}