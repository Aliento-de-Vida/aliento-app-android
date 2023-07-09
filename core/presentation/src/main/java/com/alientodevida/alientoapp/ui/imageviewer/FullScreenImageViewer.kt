package com.alientodevida.alientoapp.ui.imageviewer

import android.content.Context
import com.bumptech.glide.Glide
import com.stfalcon.imageviewer.StfalconImageViewer

fun openFullScreenImage(
    context: Context,
    images: List<String>,
    backgroundColor: Int,
) {
    StfalconImageViewer
        .Builder(context, images) { view, imageUrl ->
            Glide.with(context).load(imageUrl).into(view)
        }
        .withBackgroundColor(backgroundColor)
        .allowSwipeToDismiss(true)
        .show()
}
