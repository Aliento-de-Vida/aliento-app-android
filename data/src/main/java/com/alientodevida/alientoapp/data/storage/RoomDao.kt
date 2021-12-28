package com.alientodevida.alientoapp.data.storage

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alientodevida.alientoapp.domain.entities.local.ImageUrlEntity
import com.alientodevida.alientoapp.domain.entities.local.PodcastEntity
import com.alientodevida.alientoapp.domain.entities.local.YoutubeVideo

/**
 * MoviesDao to access our get insert methods.
 */

@Dao
interface RoomDao {
    @Query("select * from youtubevideo")
    fun getYoutubePlaylistitems(): List<YoutubeVideo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllYoutubePlaylistitems(items: List<YoutubeVideo>)

    @Query("select * from podcastentity")
    fun getPodcasts(): List<PodcastEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllPodcasts(items: List<PodcastEntity>)

    @Query("select * from imageurlentity WHERE searchUrl=:searchUrl")
    fun getImageUrl(searchUrl: String): ImageUrlEntity?

    @Query("select * from imageurlentity WHERE searchUrl=:searchUrl")
    fun getImageUrlLiveData(searchUrl: String): LiveData<ImageUrlEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertImageUrl(item: ImageUrlEntity)
}
