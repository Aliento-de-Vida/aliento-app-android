package com.alientodevida.alientoapp.app.features.church

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.app.base.BaseFragment
import com.alientodevida.alientoapp.app.databinding.FragmentChurchBinding
import com.alientodevida.alientoapp.app.utils.Constants
import com.alientodevida.alientoapp.app.utils.Utils
import com.alientodevida.alientoapp.app.utils.extensions.load
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChurchFragment : BaseFragment<FragmentChurchBinding>(R.layout.fragment_church) {
  
  private val viewModel by viewModels<ChurchViewModel>()
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    
    setupUI()
    observeViewModel()
  }
  
  private fun setupUI() {
    with(binding) {
      toolbarView.icBack.setOnClickListener { activity?.onBackPressed() }
      
      videoView.load(viewModel.usImageUrl, false)
      videoView.setOnClickListener {
        Utils.handleOnClick(requireActivity(), Constants.US_VIDEO)
      }
      
      viewModel.latestVideo?.let {
        ivLatestVideo.load(viewModel.latestVideo?.thumbnilsUrl)
        ivLatestVideo.setOnClickListener {
          viewModel.latestVideo?.let {
            Utils.handleOnClick(requireActivity(), it.id)
          }
        }
      } ?: run {
        tvLatestVideo.isGone = true
        ivLatestVideo.isGone = true
        triangle2.isGone = true
        playIcon2.isGone = true
        
      }
      
    }
  }
  
  private fun observeViewModel() {
  
  }
}