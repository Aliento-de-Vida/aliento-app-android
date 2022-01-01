package com.alientodevida.alientoapp.app.features.sermons.audio

import com.alientodevida.alientoapp.app.BR
import com.alientodevida.alientoapp.app.databinding.ItemAudioCardBinding
import com.alientodevida.alientoapp.app.recyclerview.BaseDiffCallback
import com.alientodevida.alientoapp.app.recyclerview.BaseViewHolder
import com.alientodevida.alientoapp.domain.audio.Audio
import com.alientodevida.alientoapp.domain.extensions.format
import com.alientodevida.alientoapp.domain.extensions.toDate
import com.bumptech.glide.Glide
import java.util.concurrent.TimeUnit

val audioDiffCallback = object : BaseDiffCallback() {
  override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean =
    oldItem is Audio && newItem is Audio && oldItem.uri == newItem.uri
}

class AudioViewHolder(
  binding: ItemAudioCardBinding,
  listener: Listener<Audio>
) : BaseViewHolder<Audio, ItemAudioCardBinding>(
  binding,
  BR.audio,
  listener,
) {
  
  override fun bind(item: Audio) {
    super.bind(item)
    
    with(binding) {
      val date = item.releaseDate?.toDate("yyyy-MM-dd")?.format("d MMMM yyyy") ?: ""
  
      tvAudioTitle.text = item.title
      tvAudioSubtitle.text = item.subtitle
      tvDate.text = date
      audioLength.text = "${TimeUnit.MILLISECONDS.toMinutes(item.duration.toLong())}  min"
  
      val imageUrl: String = item.imageUrl
      
      Glide.with(audioPhoto.context)
        .load(imageUrl)
        .circleCrop()
        .into(audioPhoto)
    }
  }
  
}