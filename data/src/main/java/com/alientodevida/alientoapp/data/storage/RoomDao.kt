package com.alientodevida.alientoapp.data.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alientodevida.alientoapp.domain.entities.local.Podcast
import com.alientodevida.alientoapp.domain.entities.local.YoutubeVideo

@Dao
interface RoomDao {
    @Query("select * from youtubevideo")
    fun getYoutubeVideos(): List<YoutubeVideo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertYoutubeVideos(items: List<YoutubeVideo>)

    @Query("select * from podcast")
    fun getPodcasts(): List<Podcast>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPodcasts(items: List<Podcast>)
}
