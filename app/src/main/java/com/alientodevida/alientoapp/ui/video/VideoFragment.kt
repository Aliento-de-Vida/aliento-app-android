package com.alientodevida.alientoapp.ui.video

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alientodevida.alientoapp.R
import com.alientodevida.alientoapp.databinding.FragmentVideoBinding

class VideoFragment : Fragment() {

    private lateinit var videoViewModel: VideoViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        videoViewModel = ViewModelProvider(this).get(VideoViewModel::class.java)

        val binding = FragmentVideoBinding.inflate(layoutInflater)

        return binding.root
    }
}