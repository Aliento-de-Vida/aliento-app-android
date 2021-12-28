package com.alientodevida.alientoapp.app.features.campus.list

import com.alientodevida.alientoapp.app.BR
import com.alientodevida.alientoapp.app.databinding.ItemCampusBinding
import com.alientodevida.alientoapp.app.recyclerview.BaseDiffCallback
import com.alientodevida.alientoapp.app.recyclerview.BaseViewHolder
import com.alientodevida.alientoapp.app.utils.Constants
import com.alientodevida.alientoapp.domain.campus.Campus
import com.bumptech.glide.Glide

val campusDiffCallback = object : BaseDiffCallback() {
    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean =
        oldItem is Campus && newItem is Campus && oldItem.id == newItem.id
}

class CampusViewHolder(
    binding: ItemCampusBinding,
    listener: Listener<Campus>
) : BaseViewHolder<Campus, ItemCampusBinding>(
    binding,
    BR.campus,
    listener,
) {

    override fun bind(item: Campus) {
        super.bind(item)

        with(binding) {
            tvTitle.text = item.name

            item.imageUrl?.let {
                Glide.with(ivBackground.context)
                    .load("${Constants.BASE_IMAGES_URL}/${item.imageUrl}")
                    .centerCrop()
                    .into(ivBackground)
            }
        }
    }

}