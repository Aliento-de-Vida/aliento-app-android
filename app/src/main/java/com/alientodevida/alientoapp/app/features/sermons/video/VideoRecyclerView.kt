package com.alientodevida.alientoapp.app.features.sermons.video

import android.content.Context
import android.os.Build
import android.text.Html
import androidx.core.text.HtmlCompat
import com.alientodevida.alientoapp.app.BR
import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.app.databinding.ItemVideoCardBinding
import com.alientodevida.alientoapp.app.recyclerview.BaseDiffCallback
import com.alientodevida.alientoapp.app.recyclerview.BaseViewHolder
import com.alientodevida.alientoapp.app.utils.extensions.load
import com.alientodevida.alientoapp.domain.extensions.format
import com.alientodevida.alientoapp.domain.extensions.toDate
import com.alientodevida.alientoapp.domain.gallery.Gallery
import com.alientodevida.alientoapp.domain.video.YoutubeVideo
import com.bumptech.glide.Glide

val videoDiffCallback = object : BaseDiffCallback() {
  override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean =
    oldItem is YoutubeVideo && newItem is YoutubeVideo && oldItem.id == newItem.id
}

class VideoViewHolder(
  binding: ItemVideoCardBinding,
  listener: Listener<YoutubeVideo>
) : BaseViewHolder<YoutubeVideo, ItemVideoCardBinding>(
  binding,
  BR.video,
  listener,
) {
  
  override fun bind(item: YoutubeVideo) {
    super.bind(item)
    
    with(binding) {
      audioLength.text = item.date.toDate("yyyy-MM-dd")?.format("d MMMM yyyy")
      
      audioName.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(item.name, HtmlCompat.FROM_HTML_MODE_LEGACY)
      } else {
        Html.fromHtml(item.name)
      }
      
      tvDescrption.text = item.description
      val context: Context = audioPhoto.context
  
      Glide.with(context)
        .load(R.mipmap.ic_launcher_round)
        .circleCrop()
        .into(ivAuthor)
      
      item.thumbnilsUrl?.replace("hqdefault.jpg", "maxresdefault.jpg")?.let {
        audioPhoto.load(it)
      }
    }
  }
  
}