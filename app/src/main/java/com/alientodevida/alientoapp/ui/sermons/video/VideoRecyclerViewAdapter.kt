package com.alientodevida.alientoapp.ui.sermons.video

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alientodevida.alientoapp.R
import com.alientodevida.alientoapp.data.entities.local.YoutubePlaylistItemEntity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class VideoRecyclerViewAdapter(
    var videos: List<YoutubePlaylistItemEntity>,
    private val listener: ItemClickListenerYoutube
) : RecyclerView.Adapter<VideoRecyclerViewAdapter.VideoViewHolder>() {

    class VideoViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var audioName: TextView = itemView.findViewById(R.id.audio_name)
        var audioLength: TextView = itemView.findViewById(R.id.audio_length)
        var description: TextView = itemView.findViewById(R.id.tv_descrption)
        var audioPhoto: ImageView = itemView.findViewById(R.id.audio_photo)
        var progressBar: ProgressBar = itemView.findViewById(R.id.progressBar_picture)
        fun bind(item: YoutubePlaylistItemEntity, listener: ItemClickListenerYoutube) {
            itemView.setOnClickListener { listener.onItemClick(item) }
        }

    }

    override fun getItemCount(): Int {
        return videos.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): VideoViewHolder {
        val v: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.card_item_video, viewGroup, false)
        return VideoViewHolder(v)
    }

    override fun onBindViewHolder(videoViewHolder: VideoViewHolder, i: Int) {
        try {
            val fmt = SimpleDateFormat("yyyy-MM-dd")
            val date = fmt.parse(videos[i].date)
            val fmtOut = SimpleDateFormat("d MMMM yyyy", Locale("es", "ES"))
            videoViewHolder.audioLength.text = fmtOut.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        videoViewHolder.audioName.text = videos[i].name
        videoViewHolder.description.text = videos[i].description
        val context: Context = videoViewHolder.audioPhoto.context

        videos[i].thumbnilsUrl?.replace("hqdefault.jpg", "maxresdefault.jpg")?.let {
            Glide.with(context)
                    .load(it)
                    .centerCrop()
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                            videoViewHolder.progressBar.visibility = View.GONE
                            return true
                        }

                        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            videoViewHolder.progressBar.visibility = View.GONE
                            videoViewHolder.audioPhoto.setImageDrawable(resource)
                            return true
                        }

                    })
                    .into(videoViewHolder.audioPhoto)
        }

        videoViewHolder.bind(videos[i], listener)
    }

    interface ItemClickListenerYoutube {
        fun onItemClick(item: YoutubePlaylistItemEntity)
    }
}