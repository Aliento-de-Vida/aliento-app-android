package com.alientodevida.alientoapp.ui.home

import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alientodevida.alientoapp.R
import com.alientodevida.alientoapp.data.entities.local.CategoryItem
import com.alientodevida.alientoapp.data.entities.local.CategoryItemType
import com.alientodevida.alientoapp.data.entities.local.YoutubeItem
import com.alientodevida.alientoapp.databinding.FragmentHomeBinding
import com.alientodevida.alientoapp.utils.Constants
import com.alientodevida.alientoapp.utils.Utils
import com.bumptech.glide.Glide
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ViewListener
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel by viewModels<HomeViewModel>()

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

        setupSermonsRecyclerView(binding.sermonsCarousel)

        setupCarousel(binding.carrousel)

        viewModel.isGettingData.observe(viewLifecycleOwner) { isGettingData ->
            if (isGettingData.not()) binding.swiperefresh.isRefreshing = false
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

    private fun setupSermonsRecyclerView(carouselView: CarouselView) {
        viewModel.sermonsItems.observe(viewLifecycleOwner) { result ->
            carouselView.setViewListener(viewListener)
            carouselView.pageCount = if (result.size < Companion.MAX_ITEMS_CAROUSEL) result.size else Companion.MAX_ITEMS_CAROUSEL
        }
    }

    private var viewListener: ViewListener = ViewListener { position ->
        val customView = layoutInflater.inflate(R.layout.item_sermons_carrousel, null)

        viewModel.sermonsItems.value?.get(position)?.let {

            Glide.with(customView)
                    .load(it.imageUrl)
                    .centerCrop()
                    .into(customView.findViewById(R.id.imageView))

            when (it) {
                is CategoryItem -> {
                    (customView.findViewById(R.id.play_icon) as ImageView).visibility = View.GONE
                    (customView.findViewById(R.id.triangle) as FrameLayout).visibility = View.GONE
                    (customView.findViewById(R.id.title) as TextView).text = it.title
                    customView.setOnClickListener { _ -> goToSermons()}

                }
                is YoutubeItem -> {
                    (customView.findViewById(R.id.play_icon) as ImageView).visibility = View.VISIBLE
                    (customView.findViewById(R.id.triangle) as FrameLayout).visibility = View.VISIBLE
                    (customView.findViewById(R.id.title) as TextView).visibility = View.GONE
                    customView.setOnClickListener { _ -> Utils.handleOnClick(requireActivity(), it.youtubeId)}
                }
            }

        }

        customView
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

    companion object {
        private const val MAX_ITEMS_CAROUSEL = 3
    }
}