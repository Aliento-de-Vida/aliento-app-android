package com.alientodevida.alientoapp.ui.audio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alientodevida.alientoapp.R
import com.alientodevida.alientoapp.databinding.FragmentAudioBinding
import com.alientodevida.alientoapp.databinding.FragmentVideoBinding
import com.alientodevida.alientoapp.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AudioFragment : Fragment() {

    private val audioViewModel by viewModels<AudioViewModel>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentAudioBinding.inflate(layoutInflater)

        return binding.root
    }
}