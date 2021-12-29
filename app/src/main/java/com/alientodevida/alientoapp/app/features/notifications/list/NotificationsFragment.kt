package com.alientodevida.alientoapp.app.features.notifications.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.app.base.BaseFragment
import com.alientodevida.alientoapp.app.databinding.FragmentNotificationsBinding
import com.alientodevida.alientoapp.app.databinding.ItemNotificationBinding
import com.alientodevida.alientoapp.app.recyclerview.BaseDiffAdapter
import com.alientodevida.alientoapp.app.recyclerview.BaseViewHolder
import com.alientodevida.alientoapp.domain.home.Notification
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationsFragment : BaseFragment<FragmentNotificationsBinding>(R.layout.fragment_notifications) {
  
  private val viewModel by viewModels<NotificationsViewModel>()
  
  private val notificationsAdapter = BaseDiffAdapter(notificationsDiffCallback)
  
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
        notificationsAdapter.submitList(it)
      }
    }
  }
  
  private fun setupRecyclerView() {
    val resourceListener = BaseViewHolder.Listener { notification: Notification, _ ->
      findNavController().navigate(NotificationsFragmentDirections.actionFragmentNotificationsToFragmentNotificationDetail(notification))
    }
    notificationsAdapter.register<Notification, ItemNotificationBinding, NotificationsViewHolder>(
      R.layout.item_notification,
      resourceListener,
    )
    
    binding.rvNotifications.layoutManager = LinearLayoutManager(
      requireContext(),
      LinearLayoutManager.VERTICAL,
      false
    )
    binding.rvNotifications.adapter = notificationsAdapter
  }
  
}