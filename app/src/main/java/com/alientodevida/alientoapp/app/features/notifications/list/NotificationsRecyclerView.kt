package com.alientodevida.alientoapp.app.features.notifications.list

import com.alientodevida.alientoapp.app.BR
import com.alientodevida.alientoapp.app.databinding.ItemNotificationBinding
import com.alientodevida.alientoapp.app.recyclerview.BaseDiffCallback
import com.alientodevida.alientoapp.app.recyclerview.BaseViewHolder
import com.alientodevida.alientoapp.app.utils.extensions.load
import com.alientodevida.alientoapp.app.utils.extensions.toImageUrl
import com.alientodevida.alientoapp.domain.home.Notification

val notificationsDiffCallback = object : BaseDiffCallback() {
  override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean =
    oldItem is Notification && newItem is Notification && oldItem.id == newItem.id
}

class NotificationsViewHolder(
  binding: ItemNotificationBinding,
  listener: Listener<Notification>
) : BaseViewHolder<Notification, ItemNotificationBinding>(
  binding,
  BR.notification,
  listener,
) {
  
  override fun bind(item: Notification) {
    super.bind(item)
    
    with(binding) {
      tvTitle.text = item.title
      tvContent.text = item.content
      tvDate.text = item.date
  
      ivBackground.load(item.image?.name?.toImageUrl())
    }
  }
  
}