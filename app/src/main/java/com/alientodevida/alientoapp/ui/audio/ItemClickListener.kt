package com.alientodevida.alientoapp.ui.audio

import com.alientodevida.alientoapp.data.entities.Podcasts

interface ItemClickListener {
    //void onItemClick(PredicasAudioFragment.Audio item);
    fun onItemClick(item: Podcasts)
}
