package com.alientodevida.alientoapp.ui.video

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
import com.alientodevida.alientoapp.databinding.FragmentVideoBinding
import com.alientodevida.alientoapp.ui.audio.AudioViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VideoFragment : Fragment() {

    private val videoViewModel by viewModels<VideoViewModel>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentVideoBinding.inflate(layoutInflater)

        return binding.root
    }
}