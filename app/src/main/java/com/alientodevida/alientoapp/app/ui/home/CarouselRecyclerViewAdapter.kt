package com.alientodevida.alientoapp.app.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.app.databinding.ItemCarouselBinding
import com.alientodevida.alientoapp.domain.entities.local.CarouselItem
import com.alientodevida.alientoapp.domain.entities.local.CategoryItem
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
                DataBindingUtil.inflate(LayoutInflater.from(parent.context),
                    CarouselItemViewHolder.LAYOUT, parent, false)
        return CarouselItemViewHolder(withDataBinding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: CarouselItemViewHolder, position: Int) {
        holder.deviceDataBinding.also {
            val item = items[position]

            it.item = item
            it.callback = callback

            it.title.text = item.title

            Glide.with(holder.deviceDataBinding.imageView.context)
                    .load(item.imageUrl)
                    .centerCrop()
                    .into(holder.deviceDataBinding.imageView)

            it.triangle.visibility = if (item is CategoryItem) View.GONE else View.VISIBLE
            it.playIcon.visibility = if (item is CategoryItem) View.GONE else View.VISIBLE

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