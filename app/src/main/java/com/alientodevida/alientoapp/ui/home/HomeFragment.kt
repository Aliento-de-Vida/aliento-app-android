package com.alientodevida.alientoapp.ui.home

import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alientodevida.alientoapp.data.entities.local.CategoryItem
import com.alientodevida.alientoapp.data.entities.local.CategoryItemType
import com.alientodevida.alientoapp.data.entities.local.YoutubeItem
import com.alientodevida.alientoapp.databinding.FragmentHomeBinding
import com.alientodevida.alientoapp.utils.Constants
import com.alientodevida.alientoapp.utils.Utils
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel by viewModels<HomeViewModel>()

    private lateinit var sermonsRecyclerViewAdapter: CarouselRecyclerViewAdapter
    private lateinit var carouselRecyclerViewAdapter: CarouselRecyclerViewAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val binding = FragmentHomeBinding.inflate(layoutInflater)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        setupUI(binding)

        return binding.root
    }

    private fun setupUI(binding: FragmentHomeBinding) {

        binding.swiperefresh.setOnRefreshListener {
            viewModel.refreshSermonItems()
            viewModel.refreshCategoriesCarousel()
            viewModel.refreshQuickLinks()
        }

        val content = SpannableString("Ver más")
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        binding.verMas.text = content
        binding.verMas.setOnClickListener { goToSermons() }

        setupSermonsRecyclerView(binding.sermons)

        setupCarousel(binding.carrousel)

        viewModel.isGettingData.observe(viewLifecycleOwner) { isGettingData ->
            if (isGettingData.not()) binding.swiperefresh.isRefreshing = false
        }

        binding.sermons.setOnClickListener {
            goToSermons()
        }
        binding.donations.setOnClickListener {
            goToDonations()
        }
        binding.prayer.setOnClickListener {
            goToPrayer()
        }
        binding.webPage.setOnClickListener {
            Utils.goToUrl(requireContext(), Constants.webPageUrl)
        }
        binding.ebook.setOnClickListener {
            Utils.goToUrl(requireContext(), Constants.ebookDownloadUrl)
        }

        binding.instagram.setOnClickListener {
            Utils.openInstagramPage(requireContext())
        }
        binding.youtube.setOnClickListener {
            Utils.openYoutubeChannel(requireContext(), Constants.YOUTUBE_CHANNEL_URL)
        }
        binding.facebook.setOnClickListener {
            Utils.openFacebookPage(requireContext())
        }
        binding.twitter.setOnClickListener {
            Utils.openTwitterPage(requireContext())
        }
        binding.spotify.setOnClickListener {
            Utils.openSpotifyArtistPage(requireContext(), Constants.SPOTIFY_ARTIST_ID)
        }
    }

    private fun setupSermonsRecyclerView(recyclerView: RecyclerView) {
        sermonsRecyclerViewAdapter = CarouselRecyclerViewAdapter(ItemClick { item ->
            when (item) {
                is CategoryItem -> goToSermons()
                is YoutubeItem -> Utils.handleOnClick(requireActivity(), item.youtubeId)
            }
        })

        viewModel.sermonsItems.observe(viewLifecycleOwner) { result ->
            sermonsRecyclerViewAdapter.items = result
            sermonsRecyclerViewAdapter.notifyDataSetChanged()
            recyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = sermonsRecyclerViewAdapter
            }
        }
    }

    private fun setupCarousel(carrousel: RecyclerView) {
        carouselRecyclerViewAdapter = CarouselRecyclerViewAdapter(ItemClick { item ->
            when ((item as CategoryItem).type) {
                CategoryItemType.CHURCH -> goToChurch()
                CategoryItemType.MANOS_EXTENDIDAS -> Utils.showComingSoon(requireContext())
                CategoryItemType.CURSOS -> Utils.showComingSoon(requireContext())
                CategoryItemType.SERMONS -> goToSermons()
            }
        })

        viewModel.carouseItems.observe(viewLifecycleOwner) { result: List<CategoryItem> ->
            carouselRecyclerViewAdapter.items = result
            carouselRecyclerViewAdapter.notifyDataSetChanged()
            carrousel.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = carouselRecyclerViewAdapter
            }
        }
    }

    private fun goToSermons() {
        val action = HomeFragmentDirections.actionNavigationHomeToNavigationSermons()
        findNavController().navigate(action)
    }

    private fun goToChurch() {
        val action = HomeFragmentDirections.actionNavigationHomeToChurchFragment()
        findNavController().navigate(action)
    }

    private fun goToDonations() {
        val action = HomeFragmentDirections.actionNavigationHomeToDonationsFragment()
        findNavController().navigate(action)
    }

    private fun goToPrayer() {
        val action = HomeFragmentDirections.actionNavigationHomeToPrayerFragment()
        findNavController().navigate(action)
    }
}