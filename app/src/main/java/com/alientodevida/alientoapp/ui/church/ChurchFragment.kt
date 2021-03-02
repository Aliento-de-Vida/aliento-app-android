package com.alientodevida.alientoapp.ui.church

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.alientodevida.alientoapp.databinding.FragmentChurchBinding
import com.alientodevida.alientoapp.utils.Constants
import com.alientodevida.alientoapp.utils.Utils
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

    private fun setupUI(binding: FragmentChurchBinding) {

        binding.onlineTransmision.setOnClickListener {
            Utils.goToUrl(requireContext(), Constants.webPageUrl)
        }
    }
}