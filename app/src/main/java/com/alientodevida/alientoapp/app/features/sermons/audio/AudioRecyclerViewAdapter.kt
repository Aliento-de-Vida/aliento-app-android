package com.alientodevida.alientoapp.app.features.sermons.audio

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.domain.entities.local.Audio
import com.alientodevida.alientoapp.domain.extensions.format
import com.alientodevida.alientoapp.domain.extensions.toDate
import com.alientodevida.alientoapp.domain.spotify.asDomain
import com.bumptech.glide.Glide
import java.util.*
import java.util.concurrent.TimeUnit

class AudioRecyclerViewAdapter(
  private val context: Context,
  var audio: ArrayList<Audio>,
  private val listener: ItemClickListener
) : RecyclerView.Adapter<AudioRecyclerViewAdapter.AudioViewHolder>() {
  
  class AudioViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var title: TextView = itemView.findViewById(R.id.tv_audio_title)
    var subtitle: TextView = itemView.findViewById(R.id.tv_audio_subtitle)
    var date: TextView = itemView.findViewById(R.id.tv_date)
    var audioLength: TextView = itemView.findViewById(R.id.audio_length)
    var audioPhoto: ImageView = itemView.findViewById(R.id.audio_photo)
    
    fun bind(item: Audio, listener: ItemClickListener) {
      itemView.setOnClickListener { listener.onItemClick(item) }
    }
    
  }
  
  override fun getItemCount(): Int {
    return audio.size
  }
  
  override fun onCreateViewHolder(
    viewGroup: ViewGroup,
    i: Int
  ): AudioViewHolder {
    val v: View =
      LayoutInflater.from(viewGroup.context)
        .inflate(R.layout.item_audio_card, viewGroup, false)
    return AudioViewHolder(v)
  }
  
  override fun onBindViewHolder(
    audioViewHolder: AudioViewHolder,
    i: Int
  ) {
    val date = audio[i].releaseDate?.toDate("yyyy-MM-dd")?.format("d MMMM yyyy") ?: ""
  
    audioViewHolder.title.text = audio[i].title
    audioViewHolder.subtitle.text = audio[i].subtitle
    audioViewHolder.date.text = date
    audioViewHolder.audioLength.text =
      "${TimeUnit.MILLISECONDS.toMinutes(audio[i].duration.toLong())}  min"
    
    val imageUrl: String = audio[i].imageUrl
    Glide.with(context)
      .load(imageUrl)
      .circleCrop()
      .into(audioViewHolder.audioPhoto)
    audioViewHolder.bind(audio[i], listener)
  }
  
  interface ItemClickListener {
    fun onItemClick(item: Audio)
  }
}