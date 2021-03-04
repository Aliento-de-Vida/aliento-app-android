package com.alientodevida.alientoapp.ui.prayer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.alientodevida.alientoapp.databinding.FragmentDonationsBinding

class PrayerFragment : Fragment() {

    private val viewModel by viewModels<PrayerViewModel>()

    companion object {
        fun newInstance() = PrayerFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentDonationsBinding.inflate(layoutInflater)

        binding.lifecycleOwner = viewLifecycleOwner
        //binding.viewModel = viewModel


        return binding.root
    }
}

























