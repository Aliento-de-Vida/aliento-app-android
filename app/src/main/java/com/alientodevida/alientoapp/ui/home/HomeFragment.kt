package com.alientodevida.alientoapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.alientodevida.alientoapp.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentHomeBinding.inflate(layoutInflater)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        setupUI(binding)

        return binding.root
    }

    private fun setupUI(binding: FragmentHomeBinding) {

        binding.swiperefresh.setOnRefreshListener {
            refreshImages()
        }

        viewModel.reunionDomingos.observe(viewLifecycleOwner, {
            if (it == null) viewModel.refreshReunionDomingo()
            binding.swiperefresh.isRefreshing = false
        })

        viewModel.unoLaCongre.observe(viewLifecycleOwner, {
            if (it == null) viewModel.refreshUnoLaCongre()
            binding.swiperefresh.isRefreshing = false
        })

        viewModel.unoSomos.observe(viewLifecycleOwner, {
            if (it == null) viewModel.refreshUnoSomos()
            binding.swiperefresh.isRefreshing = false
        })

        viewModel.primers.observe(viewLifecycleOwner, {
            if (it == null) viewModel.refreshPrimers()
            binding.swiperefresh.isRefreshing = false
        })

        viewModel.gruposGeneradores.observe(viewLifecycleOwner, {
            if (it == null) viewModel.refreshGruposGeneradores()
            binding.swiperefresh.isRefreshing = false
        })
    }

    private fun refreshImages() {
        viewModel.refreshReunionDomingo()
        viewModel.refreshUnoLaCongre()
        viewModel.refreshUnoSomos()
        viewModel.refreshPrimers()
        viewModel.refreshGruposGeneradores()
    }
}