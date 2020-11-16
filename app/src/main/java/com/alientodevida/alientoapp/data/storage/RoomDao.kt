package com.alientodevida.alientoapp.data.storage

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alientodevida.alientoapp.data.entities.local.ImageUrlEntity
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

    @Query("select * from imageurlentity WHERE searchUrl=:searchUrl ")
    fun getImageUrl(searchUrl: String): LiveData<ImageUrlEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertImageUrl(item: ImageUrlEntity)
}
