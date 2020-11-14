package com.alientodevida.alientoapp.ui.audio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.alientodevida.alientoapp.databinding.FragmentAudioBinding
import com.alientodevida.alientoapp.databinding.FragmentVideoBinding
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