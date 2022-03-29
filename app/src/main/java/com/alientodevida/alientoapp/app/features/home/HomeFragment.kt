package com.alientodevida.alientoapp.app.features.home

import android.os.Bundle
import android.view.HapticFeedbackConstants
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.app.base.BaseFragment
import com.alientodevida.alientoapp.app.databinding.FragmentHomeBinding
import com.alientodevida.alientoapp.app.databinding.ItemCarouselBinding
import com.alientodevida.alientoapp.app.recyclerview.BaseDiffAdapter
import com.alientodevida.alientoapp.app.recyclerview.BaseViewHolder
import com.alientodevida.alientoapp.app.state.Message
import com.alientodevida.alientoapp.app.state.ViewModelResult
import com.alientodevida.alientoapp.app.utils.Constants
import com.alientodevida.alientoapp.app.utils.Utils
import com.alientodevida.alientoapp.app.utils.extensions.*
import com.alientodevida.alientoapp.domain.entities.local.CarouselItem
import com.alientodevida.alientoapp.domain.entities.local.CategoryItemType
import com.synnapps.carouselview.ViewListener
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
  
  private val viewModel by viewModels<HomeViewModel>()
  
  private val carouselAdapter = BaseDiffAdapter(carouselDiffCallback)
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    
    setupUI()
    observeViewModel()
  }
  
  private fun setupUI() {
    binding.toolbarView.icSettings.setOnClickListener { goToSettings() }
    binding.toolbarView.icNotifications.setOnClickListener { goToNotifications() }
    
    binding.swiperefresh.setOnRefreshListener { this@HomeFragment.viewModel.getHome() }
    
    setupCarousel()
    setupQuickAccess()
    setupSocialMedia()
  }
  
  private fun observeViewModel() {
    viewModel.sermonsItems.observe(viewLifecycleOwner) { result ->
      viewModelResult(
        result = result,
        progressBar = binding.progressBar,
      ) { items ->
        binding.swiperefresh.isRefreshing = false
        
        binding.sermonsCarousel.setViewListener(viewListener)
        binding.sermonsCarousel.pageCount =
          if (items.size < MAX_ITEMS_CAROUSEL) items.size else MAX_ITEMS_CAROUSEL
      }
    }
  }
  
  private var viewListener: ViewListener = ViewListener { position ->
    val customView = layoutInflater.inflate(R.layout.item_sermons_carrousel, null)
    
    (viewModel.sermonsItems.value as? ViewModelResult.Success)?.data?.get(position)?.let {
      it.imageUrl?.let {
        customView.findViewById<ImageView>(R.id.imageView).load(it)
      }
      
      when {
        it.categoryItem != null -> {
          (customView.findViewById(R.id.play_icon) as ImageView).visibility = View.GONE
          (customView.findViewById(R.id.triangle) as FrameLayout).visibility = View.GONE
          (customView.findViewById(R.id.title) as TextView).text = it.title
          customView.setOnClickListener { goToSermons() }
        }
        it.youtubeItem != null -> {
          (customView.findViewById(R.id.play_icon) as ImageView).visibility = View.VISIBLE
          (customView.findViewById(R.id.triangle) as FrameLayout).visibility =
            View.VISIBLE
          (customView.findViewById(R.id.cl_title) as ConstraintLayout).visibility = View.GONE
          customView.setOnClickListener { _ ->
            Utils.handleOnClick(requireActivity(), it.youtubeItem!!.youtubeId)
          }
        }
      }
      
    }
    
    customView
  }
  
  private fun setupCarousel() {
    val resourceListener = BaseViewHolder.Listener { item: CarouselItem, _ ->
      when (item.categoryItem?.type) {
        CategoryItemType.CHURCH -> goToChurch()
        CategoryItemType.CAMPUSES -> goToCampus()
        CategoryItemType.GALLERY -> goToGallery()
        CategoryItemType.SERMONS -> goToSermons()
      }
    }
    carouselAdapter.register<CarouselItem, ItemCarouselBinding, CarouselViewHolder>(
      R.layout.item_carousel,
      resourceListener,
    )
  
    binding.carrousel.layoutManager = LinearLayoutManager(
      requireContext(),
      LinearLayoutManager.HORIZONTAL,
      false
    )
    binding.carrousel.adapter = carouselAdapter
  
    carouselAdapter.submitList(viewModel.carouseItems)
  }
  
  private fun setupQuickAccess() {
    with(binding) {
      donations.setOnClickListener { goToDonations() }
      ivDonations.load(Constants.DONATIONS_IMAGE)
      
      prayer.setOnClickListener { goToPrayer() }
      ivPrayer.load(Constants.PRAYER_IMAGE)
      
      //            webPage.setOnClickListener { Utils.goToUrl(requireContext(), Constants.webPageUrl) }
      //            ivDonations.load("https://todoserver-peter.herokuapp.com/v1/files/donaciones.png")
      
      ebook.setOnClickListener {
        (viewModel.home.value as? ViewModelResult.Success)?.data?.let {
          Utils.goToUrl(requireContext(), it.ebook)
        }
      }
      ivEbook.load(Constants.EBOOK_IMAGE)
    }
  }
  
  private fun setupSocialMedia() {
    with(binding) {
      instagram.setOnClickListener {
        (viewModel.home.value as? ViewModelResult.Success)?.data?.let {
          requireActivity().openInstagramPage(it.socialMedia.instagramUrl)
        }
      }
      youtube.setOnClickListener {
        (viewModel.home.value as? ViewModelResult.Success)?.data?.let {
          requireActivity().openYoutubeChannel(it.socialMedia.youtubeChannelUrl)
        }
      }
      facebook.setOnClickListener {
        (viewModel.home.value as? ViewModelResult.Success)?.data?.let {
          requireActivity().openFacebookPage(
            it.socialMedia.facebookPageId,
            it.socialMedia.facebookPageUrl
          )
        }
      }
      twitter.setOnClickListener {
        (viewModel.home.value as? ViewModelResult.Success)?.data?.let {
          requireActivity().openTwitterPage(it.socialMedia.twitterUserId, it.socialMedia.twitterUrl)
        }
      }
      spotify.setOnClickListener {
        (viewModel.home.value as? ViewModelResult.Success)?.data?.let {
          requireActivity().openSpotifyArtistPage(it.socialMedia.spotifyArtistId)
        }
      }
  
      setupAdminEntrypoint()
    }
  }
  
  private fun setupAdminEntrypoint() {
    var twitterLongClickCount = 0
    var spotifyLongClickCount = 0
  
    binding.twitter.setOnLongClickListener {
      it.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING)
      twitterLongClickCount++
      true
    }
  
    binding.spotify.setOnLongClickListener {
      it.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY, HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING)
      spotifyLongClickCount++
      if (spotifyLongClickCount == 1 && twitterLongClickCount == 1) {
        twitterLongClickCount = 0
        spotifyLongClickCount = 0
        
        if (viewModel.isAdmin) {
          viewModel.signAdminOut()
          showToast(Message.Localized(
            UUID.randomUUID().mostSignificantBits,
            Message.Type.INFORMATIONAL,
            R.drawable.ic_check_48,
            "Signed Out!",
            "",
            "",
          ))
        } else {
          val action = HomeFragmentDirections.actionFragmentHomeToAdminNavigation()
          findNavController().navigate(action)
        }
      }
      true
    }
  }
  
  private fun goToNotifications() {
    val action = HomeFragmentDirections.actionFragmentHomeToNotificationNavigation()
    findNavController().navigate(action)
  }
  
  private fun goToGallery() {
    val action = HomeFragmentDirections.actionFragmentHomeToGalleriesFragment()
    findNavController().navigate(action)
  }
  
  private fun goToCampus() {
    val action = HomeFragmentDirections.actionFragmentHomeToCampusFragment()
    findNavController().navigate(action)
  }
  
  private fun goToSettings() {
    val action = HomeFragmentDirections.actionNavigationHomeToSettingsFragment()
    findNavController().navigate(action)
  }
  
  private fun goToSermons() {
    val action = HomeFragmentDirections.actionNavigationHomeToNavigationSermons()
    findNavController().navigate(action)
  }
  
  private fun goToChurch() {
    val action = HomeFragmentDirections.actionNavigationHomeToChurchFragment(viewModel.latestVideo)
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
