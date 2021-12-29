package com.alientodevida.alientoapp.app.features.church

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.viewModels
import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.app.base.BaseFragment
import com.alientodevida.alientoapp.app.databinding.FragmentChurchBinding
import com.alientodevida.alientoapp.app.utils.Constants
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChurchFragment: BaseFragment<FragmentChurchBinding>(R.layout.fragment_church) {

    private val viewModel by viewModels<ChurchViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        observeViewModel()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupUI() {
        with(binding) {
            toolbarView.icBack.setOnClickListener { activity?.onBackPressed() }

            Glide.with(requireContext())
                .load(Constants.CHURCH_IMAGE)
                .into(videoView)

            transmisionWv.apply {
                setBackgroundColor(Color.TRANSPARENT)
                settings.useWideViewPort = true
                settings.javaScriptEnabled = true
                webViewClient = object : WebViewClient() {}
            }
        }
    }

    private fun observeViewModel() {

    }
}