package com.alientodevida.alientoapp.app.ui.church

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.alientodevida.alientoapp.app.databinding.FragmentChurchBinding
import com.alientodevida.alientoapp.domain.entities.network.base.ResponseError
import com.alientodevida.alientoapp.app.utils.Utils
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
        observeViewModel(binding)

        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupUI(binding: FragmentChurchBinding) {
        with(binding) {
            toolbarView.icBack.setOnClickListener { activity?.onBackPressed() }

            transmisionWv.apply {
                setBackgroundColor(Color.TRANSPARENT)
                settings.useWideViewPort = true
                settings.javaScriptEnabled = true
                webViewClient = object : WebViewClient() { }
            }
        }
    }

    private fun observeViewModel(binding: FragmentChurchBinding) {
        viewModel.transmission.observe(viewLifecycleOwner) {
            binding.date.text = Utils.format(Utils.dateFrom(it.fechaPublicacion), "dd 'de' MMMM 'de' YYYY")
            binding.transmisionWv.loadData(it.video, "text/html", "UTF-8")
        }

        viewModel.onError.observe(viewLifecycleOwner) { onError ->
            onError?.let {
                when(onError.result) {
                    is ResponseError.ApiResponseError<*> -> Toast.makeText(requireContext(), "ApiError", Toast.LENGTH_SHORT).show()
                    is ResponseError.NetworkResponseError -> Toast.makeText(requireContext(), "NetworkError", Toast.LENGTH_SHORT).show()
                    is ResponseError.UnknownResponseError -> Toast.makeText(requireContext(), "UnknownError", Toast.LENGTH_SHORT).show()
                }
                viewModel.errorHandled()
            }
        }
    }
}