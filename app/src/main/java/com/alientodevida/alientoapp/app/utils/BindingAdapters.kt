package com.alientodevida.alientoapp.app.utils

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

/**
 * Binding adapter used to display images from URL using Glide
 */
@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String?) {
    if (url != null) {
        Glide.with(imageView.context).load(url.replace("http:", "https:")).into(imageView)
    }
}

/**
 * Binding adapter used to hide the spinner once data is available.
 */
@BindingAdapter("isGettingData")
fun showIfLoadingData(view: View, isGettingData: Boolean) {
    view.visibility = if (isGettingData) View.VISIBLE else View.GONE
}