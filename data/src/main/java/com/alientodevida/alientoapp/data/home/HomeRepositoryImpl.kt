package com.alientodevida.alientoapp.data.home

import com.alientodevida.alientoapp.domain.extensions.toImageUrl
import com.alientodevida.alientoapp.domain.file.FileRepository
import com.alientodevida.alientoapp.domain.home.Home
import com.alientodevida.alientoapp.domain.home.HomeImages
import com.alientodevida.alientoapp.domain.home.HomeRepository
import com.alientodevida.alientoapp.domain.home.HomeImages as DomainHomeImages

class HomeRepositoryImpl(
  private val api: HomeApi,
  private val fileRepository: FileRepository
) : HomeRepository {
  
  override suspend fun getHome() = api.getHome()
  
  override suspend fun updateHome(home: Home): Home =
    api.updateHome(
      ebook = home.ebook,
      youtubePlaylistId = home.youtubePlaylistId,
      youtubeChannelId = home.youtubeChannelId,
      spotifyPlaylistId = home.spotifyPlaylistId,
      prayerEmail = home.prayerEmail,
      instagramUrl = home.socialMedia.instagramUrl,
      youtubeChannelUrl = home.socialMedia.youtubeChannelUrl,
      facebookPageId = home.socialMedia.facebookPageId,
      facebookPageUrl = home.socialMedia.facebookPageUrl,
      twitterUserId = home.socialMedia.twitterUserId,
      twitterUrl = home.socialMedia.twitterUrl,
      spotifyArtistId = home.socialMedia.spotifyArtistId,
    )
  
  override suspend fun getHomeImages(): HomeImages {
    val images = fileRepository.getAllImages().map {
      it.replace("/uploads", "").toImageUrl()
    }
    
    val sermonImage = images.lastOrNull { it.contains(DomainHomeImages.SERMONS_IMAGE) }
    val churchImage = images.lastOrNull { it.contains(DomainHomeImages.CHURCH_IMAGE) }
    val campusImage = images.lastOrNull { it.contains(DomainHomeImages.CAMPUS_IMAGE) }
    val galleriesImage = images.lastOrNull { it.contains(DomainHomeImages.GALLERY_IMAGE) }
    val donationsImage = images.lastOrNull { it.contains(DomainHomeImages.DONATIONS_IMAGE) }
    val prayerImage = images.lastOrNull { it.contains(DomainHomeImages.PRAYER_IMAGE) }
    val ebookImage = images.lastOrNull { it.contains(DomainHomeImages.EBOOK_IMAGE) }
    
    return HomeImages(
      sermonImage,
      churchImage,
      campusImage,
      galleriesImage,
      donationsImage,
      prayerImage,
      ebookImage,
    )
  }
  
}