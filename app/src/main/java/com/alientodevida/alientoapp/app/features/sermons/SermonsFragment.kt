package com.alientodevida.alientoapp.app.features.sermons

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.app.base.BaseFragment
import com.alientodevida.alientoapp.app.databinding.FragmentSermonsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SermonsFragment : BaseFragment<FragmentSermonsBinding>(R.layout.fragment_sermons) {

    private val viewModel by viewModels<SermonsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI(binding)
    }

    private fun setupUI(binding: FragmentSermonsBinding) {
        with(binding) {
            toolbarView.icBack.setOnClickListener { activity?.onBackPressed() }

            val fragmentContainer = root.findViewById<View>(R.id.nav_host_fragment_sermons)
            val navController = Navigation.findNavController(fragmentContainer)

            val bottomNavigationView = navView
            bottomNavigationView.setupWithNavController(navController)
        }
    }
}