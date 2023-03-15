package com.alientodevida.alientoapp.ui.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.load(url: String?, centerCrop: Boolean = true) {
    if (centerCrop) {
        Glide.with(context)
            .load(url ?: "")
            .centerCrop()
            .into(this)
    } else {
        Glide.with(context)
            .load(url ?: "")
            .into(this)
    }
}
