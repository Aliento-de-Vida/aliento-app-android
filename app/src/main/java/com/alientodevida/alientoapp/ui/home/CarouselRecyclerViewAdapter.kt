package com.alientodevida.alientoapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.alientodevida.alientoapp.R
import com.alientodevida.alientoapp.data.entities.local.CarouselItem
import com.alientodevida.alientoapp.data.entities.local.CategoryItem
import com.alientodevida.alientoapp.data.entities.local.YoutubeItem
import com.alientodevida.alientoapp.databinding.ItemCarouselBinding
import com.bumptech.glide.Glide

class ItemClick(val block: (CarouselItem) -> Unit) {
    fun onClick(device: CarouselItem) = block(device)
}

class CarouselItemViewHolder(val deviceDataBinding: ItemCarouselBinding) :
        RecyclerView.ViewHolder(deviceDataBinding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.item_carousel
    }
}

class CarouselRecyclerViewAdapter(private val callback: ItemClick) : RecyclerView.Adapter<CarouselItemViewHolder>() {

    var items: List<CarouselItem> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselItemViewHolder {
        val withDataBinding: ItemCarouselBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.context), CarouselItemViewHolder.LAYOUT, parent, false)
        return CarouselItemViewHolder(withDataBinding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: CarouselItemViewHolder, position: Int) {
        holder.deviceDataBinding.also {
            val item = items[position]

            it.item = item
            it.callback = callback

            it.title.text = item.title

            when (item) {
                is CategoryItem -> {
                    Glide.with(holder.deviceDataBinding.imageView.context)
                            .load(item.imageResource)
                            .centerCrop()
                            .into(holder.deviceDataBinding.imageView)
                }
                is YoutubeItem -> {
                    Glide.with(holder.deviceDataBinding.imageView.context)
                            .load(item.imageUrl)
                            .centerCrop()
                            .into(holder.deviceDataBinding.imageView)
                }
            }

            when (position) {
                0 -> {
                    val params = holder.deviceDataBinding.root.layoutParams as RecyclerView.LayoutParams
                    params.leftMargin = 20
                    params.rightMargin = 0
                    holder.deviceDataBinding.root.layoutParams = params
                }
                items.lastIndex -> {
                    val params = holder.deviceDataBinding.root.layoutParams as RecyclerView.LayoutParams
                    params.rightMargin = 20
                    params.leftMargin = 0
                    holder.deviceDataBinding.root.layoutParams = params
                }
                else -> {
                    val params = holder.deviceDataBinding.root.layoutParams as RecyclerView.LayoutParams
                    params.leftMargin = 0
                    params.rightMargin = 0
                    holder.deviceDataBinding.root.layoutParams = params
                }
            }
        }
    }

}