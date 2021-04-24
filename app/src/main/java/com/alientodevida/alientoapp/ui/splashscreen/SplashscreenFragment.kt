package com.alientodevida.alientoapp.ui.splashscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.alientodevida.alientoapp.databinding.FragmentSplashscreenBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashscreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSplashscreenBinding.inflate(layoutInflater)

        viewLifecycleOwner.lifecycleScope.launch {
            delay(1000)
            binding.progressBar.isInvisible = true

            val action = SplashscreenFragmentDirections.actionFragmentSplashscreenToMobileNavigation()
            findNavController().navigate(action)
        }

        return binding.root
    }
}
