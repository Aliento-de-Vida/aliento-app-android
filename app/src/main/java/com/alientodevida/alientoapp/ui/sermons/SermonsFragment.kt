package com.alientodevida.alientoapp.ui.sermons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.alientodevida.alientoapp.R
import com.alientodevida.alientoapp.databinding.FragmentSermonsBinding
import com.alientodevida.alientoapp.ui.church.ChurchFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SermonsFragment : Fragment() {

    private val viewModel by viewModels<SermonsViewModel>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSermonsBinding.inflate(layoutInflater)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        setupUI(binding)

        return binding.root
    }

    private fun setupUI(binding: FragmentSermonsBinding) {
        with(binding) {
            toolbarView.icSettings.setOnClickListener { goToSettings() }

            val fragmentContainer = root.findViewById<View>(R.id.nav_host_fragment_sermons)
            val navController = Navigation.findNavController(fragmentContainer)

            val bottomNavigationView = navView
            bottomNavigationView.setupWithNavController(navController)
        }
    }

    private fun goToSettings() {
        val action = SermonsFragmentDirections.actionNavigationSermonsToSettingsFragment()
        findNavController().navigate(action)
    }
}