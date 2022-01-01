package com.alientodevida.alientoapp.app.features.gallery

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.app.base.BaseBottomSheetFragment
import com.alientodevida.alientoapp.app.databinding.FragmentGalleryBinding
import com.alientodevida.alientoapp.app.databinding.ItemGalleryImageBinding
import com.alientodevida.alientoapp.app.recyclerview.BaseDiffAdapter
import com.alientodevida.alientoapp.app.utils.extensions.load
import com.alientodevida.alientoapp.app.utils.extensions.toImageUrl
import com.alientodevida.alientoapp.domain.common.Image
import com.stfalcon.imageviewer.StfalconImageViewer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleryFragment : BaseBottomSheetFragment<FragmentGalleryBinding>(R.layout.fragment_gallery) {
  
  private val viewModel by viewModels<GalleryViewModel>()
  
  private val galleryAdapter = BaseDiffAdapter(imageDiffCallback)
  private lateinit var viewer: StfalconImageViewer<Image>
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    
    setUpUi()
  }
  
  private fun setUpUi() {
    with(binding) {
      tvTitle.text = viewModel.gallery.name
      
      galleryAdapter.register<Image, ItemGalleryImageBinding>(
        itemClass = Image::class.java,
        viewType = R.layout.item_gallery_image,
        factory = ImageViewHolderFactory(
          imageSelected = { image, index, imageView ->
            viewer =
              StfalconImageViewer.Builder(
                requireContext(),
                viewModel.gallery.images
              ) { view, imageUrl ->
                view.load(imageUrl.name.toImageUrl(), false)
              }
                .withStartPosition(index)
                .withTransitionFrom(imageView).show()
          },
        ),
      )
      
      rvGallery.layoutManager = GridLayoutManager(
        requireContext(),
        3,
        LinearLayoutManager.VERTICAL,
        false,
      )
      rvGallery.adapter = galleryAdapter
      
      galleryAdapter.submitList(viewModel.gallery.images)
    }
  }
  
}
























