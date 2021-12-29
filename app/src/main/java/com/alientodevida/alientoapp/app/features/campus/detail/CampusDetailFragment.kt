package com.alientodevida.alientoapp.app.features.campus.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.app.base.BaseBottomSheetFragment
import com.alientodevida.alientoapp.app.databinding.FragmentCampusDetailBinding
import com.alientodevida.alientoapp.app.utils.extensions.load
import com.alientodevida.alientoapp.app.utils.extensions.toImageUrl
import com.bumptech.glide.Glide
import com.stfalcon.imageviewer.StfalconImageViewer
import com.synnapps.carouselview.ViewListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CampusDetailFragment :
    BaseBottomSheetFragment<FragmentCampusDetailBinding>(R.layout.fragment_campus_detail) {

    private val viewModel by viewModels<CampusDetailViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
    }

    private fun setupUI() {
        with(binding) {
            val campus = viewModel.campus

            tvTitle.text = "CAMPUS ${campus.name.uppercase()}"

            binding.carousel.setViewListener(viewListener)
            binding.carousel.pageCount =
                if (viewModel.campus.images.size < MAX_ITEMS_CAROUSEL) viewModel.campus.images.size else MAX_ITEMS_CAROUSEL

            tvShortDescription.text = campus.shortDescription
            tvFullDescription.text = campus.description
            tvContact.text = campus.contact

            btMaps.setOnClickListener {
                openMap(
                    campus.location.latitude,
                    campus.location.longitude,
                    campus.name,
                )
            }
            btGallery.setOnClickListener {
                openGallery(viewModel.campus.images.map { it.toImageUrl() })
            }
        }
    }

    private var viewListener: ViewListener = ViewListener { position ->
        val customView = layoutInflater.inflate(R.layout.item_sermons_carrousel, null)

        val item = viewModel.campus.images[position]
        customView.findViewById<ImageView>(R.id.imageView).load(item.toImageUrl())

        (customView.findViewById(R.id.play_icon) as ImageView).visibility = View.GONE
        (customView.findViewById(R.id.triangle) as FrameLayout).visibility = View.GONE
        (customView.findViewById(R.id.cl_title) as ConstraintLayout).visibility = View.GONE
        customView.setOnClickListener { openImage(item) }

        customView
    }

    private fun openImage(url: String) {
    }

    private fun openMap(latitude: String, longitude: String, name: String) {
        val gmmIntentUri = Uri.parse("geo:$latitude,$longitude?q=$latitude,$longitude($name)")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        mapIntent.resolveActivity(requireContext().packageManager)?.let {
            startActivity(mapIntent)
        }
    }

    private fun openGallery(images: List<String>) {
        StfalconImageViewer.Builder(context, images) { view, imageUrl ->
            Glide.with(requireContext()).load(imageUrl).into(view)
        }.show()
    }

    companion object {
        private const val MAX_ITEMS_CAROUSEL = 3
    }
}