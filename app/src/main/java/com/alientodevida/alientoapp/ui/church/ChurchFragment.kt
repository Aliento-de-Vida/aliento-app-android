package com.alientodevida.alientoapp.ui.church

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.alientodevida.alientoapp.databinding.FragmentChurchBinding
import com.alientodevida.alientoapp.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChurchFragment : Fragment() {

    private val viewModel by viewModels<ChurchViewModel>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val binding = FragmentChurchBinding.inflate(layoutInflater)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        setupUI(binding)

        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupUI(binding: FragmentChurchBinding) {

        binding.transmisionWv.apply {
            setBackgroundColor(Color.TRANSPARENT)
            settings.useWideViewPort = true
            settings.javaScriptEnabled = true
            webViewClient = object : WebViewClient() { }
        }
        Constants.US_VIDEO

        viewModel.transmision.observe(viewLifecycleOwner) {
            binding.transmisionWv.loadData(Constants.html, "text/html", "UTF-8")
        }
    }
}