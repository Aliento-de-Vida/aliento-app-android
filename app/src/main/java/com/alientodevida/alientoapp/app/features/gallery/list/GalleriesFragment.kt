package com.alientodevida.alientoapp.app.features.gallery.list

import android.os.Bundle
import android.view.View
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.app.base.BaseFragment
import com.alientodevida.alientoapp.app.compose.theme.AppTheme
import com.alientodevida.alientoapp.app.databinding.FragmentNotificationsBinding
import com.alientodevida.alientoapp.domain.gallery.Gallery
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleriesFragment : BaseFragment<FragmentNotificationsBinding>(R.layout.fragment_notifications) {
  
  private val viewModel by viewModels<GalleriesViewModel>()
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
  
    binding.composeView.apply {
      setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
      setContent {
        AppTheme {
          Galleries(
            viewModel = viewModel,
            onBackPressed = { activity?.onBackPressed() },
            goToEditGallery = { gallery -> goToEditGallery(gallery)},
            goToCreateGallery = { goToCreateGallery() },
          )
        }
      }
    }
  }
  
  private fun goToGallery(gallery: Gallery) = findNavController().navigate(
      GalleriesFragmentDirections.actionGalleriesFragmentToGalleryFragment(gallery)
  )
  
  private fun goToEditGallery(gallery: Gallery) = findNavController().navigate(
      GalleriesFragmentDirections.actionGalleriesFragmentToEditCreateGalleryFragment(gallery)
  )
  
  private fun goToCreateGallery() = findNavController().navigate(
      GalleriesFragmentDirections.actionGalleriesFragmentToEditCreateGalleryFragment(null)
  )
  
}