package com.alientodevida.alientoapp.app.features.gallery.list

import com.alientodevida.alientoapp.app.BR
import com.alientodevida.alientoapp.app.databinding.ItemCampusBinding
import com.alientodevida.alientoapp.app.databinding.ItemGalleryBinding
import com.alientodevida.alientoapp.app.recyclerview.BaseDiffCallback
import com.alientodevida.alientoapp.app.recyclerview.BaseViewHolder
import com.alientodevida.alientoapp.app.utils.extensions.toImageUrl
import com.alientodevida.alientoapp.domain.gallery.Gallery
import com.bumptech.glide.Glide

val galleriesDiffCallback = object : BaseDiffCallback() {
  override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean =
    oldItem is Gallery && newItem is Gallery && oldItem.name == newItem.name
}

class GalleryViewHolder(
  binding: ItemGalleryBinding,
  listener: Listener<Gallery>
) : BaseViewHolder<Gallery, ItemGalleryBinding>(
  binding,
  BR.gallery,
  listener,
) {
  
  override fun bind(item: Gallery) {
    super.bind(item)
    
    with(binding) {
      tvTitle.text = item.name
      
      Glide.with(ivBackground.context)
        .load(item.coverPicture.toImageUrl())
        .centerCrop()
        .into(ivBackground)
    }
  }
  
}