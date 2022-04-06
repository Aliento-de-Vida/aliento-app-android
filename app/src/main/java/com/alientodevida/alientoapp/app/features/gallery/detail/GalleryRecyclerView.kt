package com.alientodevida.alientoapp.app.features.gallery.detail

import android.widget.ImageView
import androidx.databinding.ViewDataBinding
import com.alientodevida.alientoapp.app.BR
import com.alientodevida.alientoapp.app.databinding.ItemGalleryImageBinding
import com.alientodevida.alientoapp.app.recyclerview.BaseDiffCallback
import com.alientodevida.alientoapp.app.recyclerview.BaseViewHolder
import com.alientodevida.alientoapp.app.utils.extensions.toImageUrl
import com.alientodevida.alientoapp.domain.common.Image
import com.bumptech.glide.Glide

val imageDiffCallback = object : BaseDiffCallback() {
  override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean =
    oldItem is Image && newItem is Image && oldItem.name == newItem.name
}

class ImageViewHolderFactory<I : Any, VDB : ViewDataBinding>(
  private val imageSelected: (Image, Int, ImageView) -> Unit,
) : BaseViewHolder.Factory<I, VDB, BaseViewHolder<I, VDB>> {
  
  @Suppress("UNCHECKED_CAST")
  override fun invoke(vdb: VDB, listener: BaseViewHolder.Listener<I>?): BaseViewHolder<I, VDB> {
    return ImageViewHolder(
      vdb as ItemGalleryImageBinding,
      imageSelected = imageSelected,
    ) as BaseViewHolder<I, VDB>
  }
}

class ImageViewHolder(
  binding: ItemGalleryImageBinding,
  private val imageSelected: (Image, Int, ImageView) -> Unit,
) : BaseViewHolder<Image, ItemGalleryImageBinding>(
  binding,
  BR.image,
) {
  
  override fun bind(item: Image) {
    super.bind(item)
    
    Glide.with(binding.ivContent.context)
      .load(item.name.toImageUrl())
      .centerCrop()
      .into(binding.ivContent)
    
    binding.root.setOnClickListener {
      imageSelected(item, this.adapterPosition, binding.ivContent)
    }
  }
  
}