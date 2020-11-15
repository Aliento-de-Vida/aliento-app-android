package com.alientodevida.alientoapp.ui.audio

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alientodevida.alientoapp.R
import com.alientodevida.alientoapp.data.entities.Podcasts
import com.alientodevida.alientoapp.databinding.FragmentAudioBinding
import com.alientodevida.alientoapp.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AudioFragment : Fragment() {

    private val viewModel by viewModels<AudioViewModel>()

    private lateinit var mAdapter: MyAdapter
    private lateinit var mLayoutManager: RecyclerView.LayoutManager

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAudioBinding.inflate(layoutInflater)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        setupUI(binding)

        viewModel.getContent()

        return binding.root
    }

    private fun setupUI(binding: FragmentAudioBinding) {

        mLayoutManager = LinearLayoutManager(context)
        binding.myRecyclerView.layoutManager = mLayoutManager

        viewModel.podcasts.observe(viewLifecycleOwner, {

            // specify an adapter (see also next example)
            mAdapter = MyAdapter(requireContext(), ArrayList(it), object : ItemClickListener {
                override fun onItemClick(item: Podcasts) {
                    handleOnClick(item)
                }
            })

            binding.myRecyclerView.adapter = mAdapter
        })

        binding.cv.setOnClickListener {
            openArtistPage()
        }

    }

    private fun openArtistPage() {
        openSpotifyWith(Uri.parse("spotify:artist:" + Constants.SPOTIFY_ARTIST_ID))
    }

    private fun handleOnClick(audio: Podcasts) {
        openSpotifyWith(Uri.parse(audio.uri))
    }

    private fun openSpotifyWith(uri: Uri) {
        if (appInstalledOrNot("com.spotify.music")) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = uri
            intent.putExtra(Intent.EXTRA_REFERRER, Uri.parse("android-app://" + requireContext().packageName))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            requireContext().startActivity(intent)

        } else {
            try {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.spotify.music")))
            } catch (ex: ActivityNotFoundException) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.spotify.music")))
            }
        }
    }

    private fun appInstalledOrNot(uri: String): Boolean {
        val pm = requireContext().packageManager
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES)
            return true
        } catch (e: PackageManager.NameNotFoundException) {
        }
        return false
    }
}