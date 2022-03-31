package com.alientodevida.alientoapp.app.features.home

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.alientodevida.alientoapp.app.base.BaseViewModel
import com.alientodevida.alientoapp.app.state.Message
import com.alientodevida.alientoapp.app.utils.errorparser.ErrorParser
import com.alientodevida.alientoapp.domain.coroutines.CoroutineDispatchers
import com.alientodevida.alientoapp.domain.entities.local.CarouselItem
import com.alientodevida.alientoapp.domain.entities.local.CategoryItem
import com.alientodevida.alientoapp.domain.entities.local.CategoryItemType
import com.alientodevida.alientoapp.domain.entities.local.YoutubeItem
import com.alientodevida.alientoapp.domain.home.Home
import com.alientodevida.alientoapp.domain.home.HomeImages
import com.alientodevida.alientoapp.domain.home.HomeRepository
import com.alientodevida.alientoapp.domain.logger.Logger
import com.alientodevida.alientoapp.domain.preferences.Preferences
import com.alientodevida.alientoapp.domain.video.VideoRepository
import com.alientodevida.alientoapp.domain.video.YoutubeVideo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
  val home: Home? = null,
  val homeImages: HomeImages,
  val carouselItems: List<CarouselItem> = emptyList(),
  val sermonItems: List<CarouselItem> = emptyList(),
  val loading: Boolean = true,
  val messages: List<Message> = emptyList(),
)

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
  
  val isAdmin = preferences.isAdmin
  
  private val _viewModelState = MutableStateFlow(
    HomeUiState(homeImages = HomeImages())
  )
  val viewModelState: StateFlow<HomeUiState> = _viewModelState
  
  var latestVideo: YoutubeVideo? = null
  
  init {
    getHome()
  }
  
  fun onMessageDismiss(id: Long) {
    val newMessages = viewModelState.value.messages.filter { it.id != id }
    _viewModelState.update { it.copy(messages = newMessages) }
  }
  
  fun getHome() {
    _viewModelState.update { it.copy(loading = true) }
    
    viewModelScope.launch {
      try {
        val images = homeRepository.getHomeImages()
        val home = homeRepository.getHome()
        preferences.home = home
        val sermons = getSermonItems(home.youtubeChannelId, images.sermonsImage)
        val carouselItems = getCategoriesItems(images)
        
        _viewModelState.update { it.copy(
          carouselItems = carouselItems,
          homeImages = images,
          home = home,
          sermonItems = sermons,
        ) }
        
      } catch (ex: CancellationException) {
        return@launch
      } catch (ex: Exception) {
        val messages = viewModelState.value.messages.toMutableList()
        messages.add(errorParser(ex))
        _viewModelState.update { it.copy(messages = messages) }
      }
  
      _viewModelState.update { it.copy(loading = false) }
    }
  }
  
  private fun getCategoriesItems(images: HomeImages) =
    listOf(
      CarouselItem(
        "Aliento de Vida",
        images.churchImage,
        CategoryItem(CategoryItemType.CHURCH),
        null,
      ),
      CarouselItem(
        "Campus",
        images.campusImage,
        CategoryItem(CategoryItemType.CAMPUSES),
        null,
      ),
      CarouselItem(
        "Galería",
        images.galleriesImage,
        CategoryItem(CategoryItemType.GALLERY),
        null,
      )
    )
  
  private suspend fun getSermonItems(channel: String, sermonsImage: String?): List<CarouselItem> {
    val sermons = videoRepository.getYoutubeChannelVideos(channel)
    latestVideo = sermons.firstOrNull()
    
    val carouselItems = mutableListOf(
      CarouselItem(
        "Ver Prédicas",
        sermonsImage,
        CategoryItem(CategoryItemType.SERMONS),
        null,
      ),
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
    return carouselItems
  }
  
  fun adminLogout() { preferences.adminToken = null }
  
}
