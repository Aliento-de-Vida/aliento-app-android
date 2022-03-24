package com.alientodevida.alientoapp.app.features.home

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.alientodevida.alientoapp.app.base.BaseViewModel
import com.alientodevida.alientoapp.app.state.ViewModelResult
import com.alientodevida.alientoapp.app.utils.Constants
import com.alientodevida.alientoapp.app.utils.errorparser.ErrorParser
import com.alientodevida.alientoapp.domain.coroutines.CoroutineDispatchers
import com.alientodevida.alientoapp.domain.entities.local.CarouselItem
import com.alientodevida.alientoapp.domain.entities.local.CategoryItem
import com.alientodevida.alientoapp.domain.entities.local.CategoryItemType
import com.alientodevida.alientoapp.domain.entities.local.YoutubeItem
import com.alientodevida.alientoapp.domain.home.Home
import com.alientodevida.alientoapp.domain.home.HomeRepository
import com.alientodevida.alientoapp.domain.logger.Logger
import com.alientodevida.alientoapp.domain.preferences.Preferences
import com.alientodevida.alientoapp.domain.video.VideoRepository
import com.alientodevida.alientoapp.domain.video.YoutubeVideo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
  private val videoRepository: VideoRepository,
  private val homeRepository: HomeRepository,
  coroutineDispatchers: CoroutineDispatchers,
  errorParser: ErrorParser,
  logger: Logger,
  preferences: Preferences,
  savedStateHandle: SavedStateHandle,
  application: Application,
) : BaseViewModel(
  coroutineDispatchers,
  errorParser,
  logger,
  preferences,
  savedStateHandle,
  application,
) {
  
  val carouseItems = listOf(
    CarouselItem(
      "Aliento de Vida",
      Constants.CHURCH_IMAGE,
      CategoryItem(CategoryItemType.CHURCH),
      null,
    ),
    CarouselItem(
      "Campus",
      Constants.CAMPUS_IMAGE,
      CategoryItem(CategoryItemType.CAMPUSES),
      null,
    ),
    CarouselItem(
      "Galería",
      Constants.GALLERY_IMAGE,
      CategoryItem(CategoryItemType.GALLERY),
      null,
    )
  )
  
  private val _sermonsItems = MutableLiveData<ViewModelResult<List<CarouselItem>>>()
  val sermonsItems = _sermonsItems
  
  private val _home = MutableLiveData<ViewModelResult<Home>>()
  val home = _home
  
  var latestVideo: YoutubeVideo? = null
  
  init {
    _sermonsItems.value = ViewModelResult.Success(
      listOf(
        CarouselItem(
          "Ver Prédicas",
          Constants.SERMONS_IMAGE,
          CategoryItem(CategoryItemType.SERMONS),
          null,
        )
      )
    )
    getHome()
  }
  
  fun getHome() {
    liveDataResult(_home) {
      val home = homeRepository.getHome()
      preferences.home = home
      getSermonItems(home.youtubeChannelId)
      home
    }
  }
  
  private fun getSermonItems(channel: String) {
    liveDataResult(_sermonsItems) {
      val sermons =
        videoRepository.getYoutubeChannelVideos(channel)
      latestVideo = sermons.firstOrNull()
      
      val carouselItems = arrayListOf<CarouselItem>()
      carouselItems += CarouselItem(
        "Ver Prédicas",
        Constants.SERMONS_IMAGE,
        CategoryItem(CategoryItemType.SERMONS),
        null,
      )
      carouselItems += sermons
        .filter { it.thumbnilsUrl != null }
        .map {
          CarouselItem(
            it.name,
            it.thumbnilsUrl!!.replace("hqdefault.jpg", "maxresdefault.jpg"),
            null,
            YoutubeItem(it.id),
          )
        }
      
      carouselItems
    }
  }
  
}
