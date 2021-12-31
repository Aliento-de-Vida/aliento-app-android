package com.alientodevida.alientoapp.app.features.notifications.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.app.base.BaseBottomSheetFragment
import com.alientodevida.alientoapp.app.databinding.FragmentNotificationDetailBinding
import com.alientodevida.alientoapp.app.utils.extensions.load
import com.alientodevida.alientoapp.app.utils.extensions.toImageUrl
import com.bumptech.glide.Glide
import com.stfalcon.imageviewer.StfalconImageViewer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationDetailFragment :
  BaseBottomSheetFragment<FragmentNotificationDetailBinding>(R.layout.fragment_notification_detail) {
  
  private val viewModel by viewModels<NotificationDetailViewModel>()
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    
    setupUI()
  }
  
  private fun setupUI() {
    with(binding) {
      val notification = viewModel.notification
      
      ivContent.load(notification.image?.name?.toImageUrl())
      
      tvTitle.text = notification.title.uppercase()
      tvDescription.text = notification.content

      btFullScreen.setOnClickListener { openInFullScreen() }
      ivContent.setOnClickListener { openInFullScreen() }
    }
  }
  
  private fun FragmentNotificationDetailBinding.openInFullScreen() {
    val imageUrl = viewModel.notification.image?.name?.toImageUrl()
    StfalconImageViewer.Builder(context, listOf(imageUrl)) { view, url ->
      Glide.with(requireContext()).load(url).into(view)
    }
      .withTransitionFrom(ivContent).show()
      .show()
  }
  
}