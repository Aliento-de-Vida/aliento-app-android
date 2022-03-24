package com.alientodevida.alientoapp.app.features.home

import android.view.View
import com.alientodevida.alientoapp.app.BR
import com.alientodevida.alientoapp.app.databinding.ItemCarouselBinding
import com.alientodevida.alientoapp.app.recyclerview.BaseDiffCallback
import com.alientodevida.alientoapp.app.recyclerview.BaseViewHolder
import com.alientodevida.alientoapp.app.utils.extensions.load
import com.alientodevida.alientoapp.domain.entities.local.CarouselItem

val carouselDiffCallback = object : BaseDiffCallback() {
  override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean =
    oldItem is CarouselItem && newItem is CarouselItem && oldItem.imageUrl == newItem.imageUrl
}

class CarouselViewHolder(
  binding: ItemCarouselBinding,
  listener: Listener<CarouselItem>
) : BaseViewHolder<CarouselItem, ItemCarouselBinding>(
  binding,
  BR.carousel_item,
  listener,
) {
  
  override fun bind(item: CarouselItem) {
    super.bind(item)
    
    with(binding) {
      title.text = item.title
  
      imageView.load(item.imageUrl)
  
      triangle.visibility = if (item.categoryItem != null) View.GONE else View.VISIBLE
      playIcon.visibility = if (item.categoryItem != null) View.GONE else View.VISIBLE
    }
  }
  
}