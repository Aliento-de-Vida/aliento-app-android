package com.alientodevida.alientoapp.ui.audio

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.alientodevida.alientoapp.R
import com.alientodevida.alientoapp.data.entities.Podcasts
import com.bumptech.glide.Glide
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class MyAdapter(
    private val context: Context,
    audios: ArrayList<Podcasts>,
    private val listener: ItemClickListener
) :
    RecyclerView.Adapter<MyAdapter.AudioViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    class AudioViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cv: CardView = itemView.findViewById(R.id.cv)
        var audioName: TextView = itemView.findViewById(R.id.audio_name)
        var audioAuthor: TextView = itemView.findViewById(R.id.audio_author)
        var audioLength: TextView = itemView.findViewById(R.id.audio_length)
        var audioPhoto: ImageView = itemView.findViewById(R.id.audio_photo)

        fun bind(item: Podcasts, listener: ItemClickListener) {
            itemView.setOnClickListener { listener.onItemClick(item) }
        }

    }

    private var audios: ArrayList<Podcasts> = audios
    override fun getItemCount(): Int {
        return audios.size
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        i: Int
    ): AudioViewHolder {
        val v: View =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.card_item, viewGroup, false)
        return AudioViewHolder(v)
    }

    override fun onBindViewHolder(
        audioViewHolder: AudioViewHolder,
        i: Int
    ) {
        var stringDate = ""
        try {
            val simpleDateFormatIn = SimpleDateFormat("yyyy-MM-dd")
            val date = simpleDateFormatIn.parse(audios[i].releaseDate)
            val simpleDateFormatOut = SimpleDateFormat("d MMMM yyyy", Locale("es", "ES"))
            stringDate = simpleDateFormatOut.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        audioViewHolder.audioName.text = audios[i].name
        audioViewHolder.audioAuthor.text = stringDate
        audioViewHolder.audioLength.text = "${TimeUnit.MILLISECONDS.toMinutes(audios[i].duration.toLong())}  min"

        val imageUrl: String = audios[i].images?.first()?.url.toString()
        Glide.with(context)
            .load(imageUrl)
            .circleCrop()
            .into(audioViewHolder.audioPhoto)
        audioViewHolder.bind(audios[i], listener)
    }
}