package com.alientodevida.alientoapp.app.features.gallery.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.app.base.BaseFragment
import com.alientodevida.alientoapp.app.databinding.FragmentGalleriesBinding
import com.alientodevida.alientoapp.app.databinding.ItemGalleryBinding
import com.alientodevida.alientoapp.app.recyclerview.BaseDiffAdapter
import com.alientodevida.alientoapp.app.recyclerview.BaseViewHolder
import com.alientodevida.alientoapp.domain.gallery.Gallery
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GalleriesFragment : BaseFragment<FragmentGalleriesBinding>(R.layout.fragment_galleries) {
  
  private val viewModel by viewModels<GalleriesViewModel>()
  
  private val galleriesAdapter = BaseDiffAdapter(galleriesDiffCallback)
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    
    setupUI()
    observeViewModel()
  }
  
  private fun setupUI() {
    binding.toolbarView.icBack.setOnClickListener { activity?.onBackPressed() }
    
    setupRecyclerView()
  }
  
  private fun observeViewModel() {
    viewModel.galleries.observe(viewLifecycleOwner) {
      viewModelResult(it, binding.progressBar) {
        galleriesAdapter.submitList(it)
      }
    }
  }
  
  private fun setupRecyclerView() {
    val resourceListener = BaseViewHolder.Listener { gallery: Gallery, _ ->
      findNavController().navigate(GalleriesFragmentDirections.actionGalleriesFragmentToGalleryFragment(gallery))
    }
    galleriesAdapter.register<Gallery, ItemGalleryBinding, GalleryViewHolder>(
      R.layout.item_gallery,
      resourceListener,
    )
    
    binding.rvGallery.layoutManager = LinearLayoutManager(
      requireContext(),
      LinearLayoutManager.VERTICAL,
      false
    )
    binding.rvGallery.adapter = galleriesAdapter
  }
  
}