package com.alientodevida.alientoapp.ui.sermons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.alientodevida.alientoapp.R
import com.alientodevida.alientoapp.databinding.FragmentSermonsBinding
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
        val fragmentContainer = binding.root.findViewById<View>(R.id.nav_host_fragment_sermons)
        val navController = Navigation.findNavController(fragmentContainer)


        //val nestedNavHostFragment = childFragmentManager.findFragmentById(R.id.nav_host_fragment_sermons) as? NavHostFragment
        //val navController = nestedNavHostFragment!!.navController

        val bottomNavigationView = binding.navView
        bottomNavigationView.setupWithNavController(navController)
    }
}