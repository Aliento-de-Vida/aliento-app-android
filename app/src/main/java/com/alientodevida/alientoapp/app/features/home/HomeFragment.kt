package com.alientodevida.alientoapp.app.features.home

import android.os.Bundle
import android.view.View
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.app.base.BaseFragment
import com.alientodevida.alientoapp.app.compose.theme.AppTheme
import com.alientodevida.alientoapp.app.databinding.FragmentNotificationsBinding
import com.alientodevida.alientoapp.app.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentNotificationsBinding>(R.layout.fragment_notifications) {
  
  private val viewModel by viewModels<HomeViewModel>()
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
  
    binding.composeView.apply {
      setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
      setContent {
        AppTheme {
          Home(
            viewModel = viewModel,
            goToEditHome = { goToEditHome() },
            goToNotifications = { goToNotifications() },
            goToSettings = { goToSettings() },
            goToSermons = { goToSermons() },
            goToChurch = { goToChurch() },
            goToCampus = { goToCampus() },
            goToGallery = { goToGallery() },
            goToPrayer = { goToPrayer() },
            goToDonations = { goToDonations() },
            goToEbook = { goToEbook() }
          )
        }
      }
    }  
  }
  
  /*private var viewListener: ViewListener = ViewListener { position ->
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
  }
  
  private fun setupQuickAccess() {
    with(binding) {
      donations.setOnClickListener { goToDonations() }
      prayer.setOnClickListener { goToPrayer() }
      ebook.setOnClickListener {
        viewModel.homeResult?.let {
          Utils.goToUrl(requireContext(), it.home.ebook)
        }
      }
    }
  }
  
  private fun setupSocialMedia() {
    with(binding) {
      instagram.setOnClickListener {
        viewModel.homeResult?.let {
          requireActivity().openInstagramPage(it.home.socialMedia.instagramUrl)
        }
      }
      youtube.setOnClickListener {
        viewModel.homeResult?.let {
          requireActivity().openYoutubeChannel(it.home.socialMedia.youtubeChannelUrl)
        }
      }
      facebook.setOnClickListener {
        viewModel.homeResult?.let {
          requireActivity().openFacebookPage(
            it.home.socialMedia.facebookPageId,
            it.home.socialMedia.facebookPageUrl
          )
        }
      }
      twitter.setOnClickListener {
        viewModel.homeResult?.let {
          requireActivity().openTwitterPage(it.home.socialMedia.twitterUserId, it.home.socialMedia.twitterUrl)
        }
      }
      spotify.setOnClickListener {
        viewModel.homeResult?.let {
          requireActivity().openSpotifyArtistPage(it.home.socialMedia.spotifyArtistId)
        }
      }
      
      setupAdminEntrypoint()
    }
  }
  
  private fun setupAdminEntrypoint() {
    var twitterLongClickCount = 0
    var spotifyLongClickCount = 0
    
    binding.twitter.setOnLongClickListener {
      it.performHapticFeedback(
        HapticFeedbackConstants.VIRTUAL_KEY,
        HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING
      )
      twitterLongClickCount++
      true
    }
    
    binding.spotify.setOnLongClickListener {
      it.performHapticFeedback(
        HapticFeedbackConstants.VIRTUAL_KEY,
        HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING
      )
      spotifyLongClickCount++
      if (spotifyLongClickCount == 1 && twitterLongClickCount == 1) {
        twitterLongClickCount = 0
        spotifyLongClickCount = 0
        
        if (viewModel.isAdmin) {
          viewModel.signAdminOut()
          binding.fabEdit.isGone = viewModel.isAdmin.not()
          showToast(
            Message.Localized(
              UUID.randomUUID().mostSignificantBits,
              Message.Type.INFORMATIONAL,
              R.drawable.ic_check_48,
              "Signed Out!",
              "",
              "",
            )
          )
        } else {
          val action = HomeFragmentDirections.actionFragmentHomeToAdminNavigation()
          findNavController().navigate(action)
        }
      }
      true
    }
  }*/
  
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
  
  private fun goToEbook() {
    // TODO is this the right way to pass ebook ?
    viewModel.viewModelState.value.home?.ebook?.let {
      Utils.goToUrl(requireContext(), it)
    }
  }
  
  private fun goToEditHome() {
    // TODO is this the right way to pass home ?
    viewModel.viewModelState.value.home?.let {
      val action = HomeFragmentDirections.actionFragmentHomeToEditHomeFragment(it)
      findNavController().navigate(action)
    }
  }
  
}
