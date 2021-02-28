package com.alientodevida.alientoapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alientodevida.alientoapp.R
import com.alientodevida.alientoapp.data.entities.local.CarrouselItem
import com.alientodevida.alientoapp.data.entities.local.CarrouselItemType
import com.alientodevida.alientoapp.databinding.FragmentHomeBinding
import com.alientodevida.alientoapp.databinding.ItemCarouselRecyclerViewBinding
import com.alientodevida.alientoapp.utils.Constants
import com.alientodevida.alientoapp.utils.Utils
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel by viewModels<HomeViewModel>()

    private lateinit var carouselRecyclerViewAdapter: CarouselRecyclerViewAdapter
    private lateinit var devicesRecyclerView: RecyclerView

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
            refreshImages()
        }

        devicesRecyclerView = binding.carrousel
        setupCarousel()

        viewModel.sermons.observe(viewLifecycleOwner) {
            binding.swiperefresh.isRefreshing = false
        }

        viewModel.donations.observe(viewLifecycleOwner) {
            binding.swiperefresh.isRefreshing = false
        }

        viewModel.webPage.observe(viewLifecycleOwner) {
            binding.swiperefresh.isRefreshing = false
        }

        viewModel.ebook.observe(viewLifecycleOwner) {
            binding.swiperefresh.isRefreshing = false
        }

        viewModel.prayer.observe(viewLifecycleOwner) {
            binding.swiperefresh.isRefreshing = false
        }


        binding.sermons.setOnClickListener {
            goToSermons()
        }
        binding.donations.setOnClickListener {
            showUnderDevelopment()
        }
        binding.prayer.setOnClickListener {
            showUnderDevelopment()
        }
        binding.webPage.setOnClickListener {
            Utils.goToUrl(requireContext(), Constants.webPageUrl)
        }
        binding.ebook.setOnClickListener {
            Utils.goToUrl(requireContext(), Constants.ebookDownloadUrl)
        }

        binding.instagram.setOnClickListener {
            showUnderDevelopment()
        }
        binding.youtube.setOnClickListener {
            Utils.openYoutubeChannel(requireContext(), Constants.YOUTUBE_CHANNEL_URL)
        }
        binding.facebook.setOnClickListener {
            showUnderDevelopment()
        }
        binding.twitter.setOnClickListener {
            showUnderDevelopment()
        }
        binding.spotify.setOnClickListener {
            Utils.openSpotifyArtistPage(requireContext(), Constants.SPOTIFY_ARTIST_ID)
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

    private fun setupCarousel() {

        viewModel.carouseItems.observe(viewLifecycleOwner) { result: List<CarrouselItem> ->
            carouselRecyclerViewAdapter = CarouselRecyclerViewAdapter(ItemClick { item ->
                when (item.type) {
                    CarrouselItemType.CHURCH -> {
                        goToChurch()
                    }
                    CarrouselItemType.MANOS_EXTENDIDAS -> {
                        showComingSoon()
                    }
                    CarrouselItemType.CURSOS -> {
                        showComingSoon()
                    }
                }
            })
            carouselRecyclerViewAdapter.items = result
            devicesRecyclerView.apply {

                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = carouselRecyclerViewAdapter
            }
        }
    }

    private fun showUnderDevelopment() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage(R.string.under_development)
            .setPositiveButton(R.string.ok) { _, _ -> }
        builder.create().show()
    }

    private fun showComingSoon() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage(R.string.comming_soon)
            .setPositiveButton(R.string.ok) { _, _ -> }
        builder.create().show()
    }

    private fun refreshImages() {
        viewModel.refreshSermonsImage()
        viewModel.refreshDonationsImage()
        viewModel.refreshWebPageImage()
        viewModel.refreshEbookImage()
        viewModel.refreshPrayerImage()
    }
}



/**
 * Carousel Recycler View Adapter
 * */
class ItemClick(val block: (CarrouselItem) -> Unit) {
    fun onClick(device: CarrouselItem) = block(device)
}

class CarouselRecyclerViewAdapter(private val callback: ItemClick) : RecyclerView.Adapter<CarouselItemViewHolder>() {

    var items: List<CarrouselItem> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselItemViewHolder {
        val withDataBinding: ItemCarouselRecyclerViewBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), CarouselItemViewHolder.LAYOUT, parent, false)
        return CarouselItemViewHolder(withDataBinding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: CarouselItemViewHolder, position: Int) {
        holder.deviceDataBinding.also {
            it.item = items[position]
            it.callback = callback

            if (it.item!!.imageResource != null) {
                Glide.with(holder.deviceDataBinding.imageView.context)
                    .load(it.item!!.imageResource)
                    .centerCrop()
                    .into(holder.deviceDataBinding.imageView)

            } else if (it.item!!.imageUrl != null) {
                Glide.with(holder.deviceDataBinding.imageView.context)
                    .load(it.item!!.imageUrl)
                    .centerCrop()
                    .into(holder.deviceDataBinding.imageView)
            }


            when (position) {
                0 -> {
                    val params = holder.deviceDataBinding.root.layoutParams as RecyclerView.LayoutParams
                    params.leftMargin = 20
                    params.rightMargin = 0
                    holder.deviceDataBinding.root.layoutParams = params
                }
                items.lastIndex -> {
                    val params = holder.deviceDataBinding.root.layoutParams as RecyclerView.LayoutParams
                    params.rightMargin = 20
                    params.leftMargin = 0
                    holder.deviceDataBinding.root.layoutParams = params
                }
                else -> {
                    val params = holder.deviceDataBinding.root.layoutParams as RecyclerView.LayoutParams
                    params.leftMargin = 0
                    params.rightMargin = 0
                    holder.deviceDataBinding.root.layoutParams = params
                }
            }
        }
    }

}

class CarouselItemViewHolder(val deviceDataBinding: ItemCarouselRecyclerViewBinding) :
    RecyclerView.ViewHolder(deviceDataBinding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.item_carousel_recycler_view
    }
}