package com.alientodevida.alientoapp.ui.audio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alientodevida.alientoapp.R
import com.alientodevida.alientoapp.databinding.FragmentAudioBinding
import com.alientodevida.alientoapp.databinding.FragmentVideoBinding

class AudioFragment : Fragment() {

    private lateinit var audioViewModel: AudioViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        audioViewModel =
                ViewModelProvider(this).get(AudioViewModel::class.java)

        val binding = FragmentAudioBinding.inflate(layoutInflater)

        return binding.root
    }
}