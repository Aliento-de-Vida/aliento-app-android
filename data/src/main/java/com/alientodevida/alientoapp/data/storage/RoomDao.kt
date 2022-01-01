package com.alientodevida.alientoapp.data.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alientodevida.alientoapp.domain.entities.local.Audio
import com.alientodevida.alientoapp.domain.video.YoutubeVideo

@Dao
interface RoomDao {
  @Query("select * from youtubevideo")
  fun getVideos(): List<YoutubeVideo>
  
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertVideos(items: List<YoutubeVideo>)
  
  @Query("select * from audio")
  fun getAudios(): List<Audio>
  
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertAudios(items: List<Audio>)
}
