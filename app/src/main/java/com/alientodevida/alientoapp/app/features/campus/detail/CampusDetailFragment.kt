package com.alientodevida.alientoapp.app.features.campus.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.app.base.BaseBottomSheetFragment
import com.alientodevida.alientoapp.app.databinding.FragmentCampusDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CampusDetailFragment : BaseBottomSheetFragment<FragmentCampusDetailBinding>(R.layout.fragment_campus_detail) {

    private val viewModel by viewModels<CampusDetailViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        observeViewModel()
    }

    private fun setupUI() {
    }

    private fun observeViewModel() {
    }

}