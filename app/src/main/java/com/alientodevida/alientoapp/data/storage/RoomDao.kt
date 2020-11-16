package com.alientodevida.alientoapp.data.storage

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alientodevida.alientoapp.data.entities.local.PodcastEntity
import com.alientodevida.alientoapp.data.entities.local.YoutubePlaylistItemEntity

/**
 * MoviesDao to access our get insert methods.
 */

@Dao
interface RoomDao {
    @Query("select * from youtubeplaylistitementity")
    fun getYoutubePlaylistitems(): LiveData<List<YoutubePlaylistItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllYoutubePlaylistitems(items: List<YoutubePlaylistItemEntity>)

    @Query("select * from podcastentity")
    fun getPodcasts(): LiveData<List<PodcastEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllPodcasts(items: List<PodcastEntity>)
}
