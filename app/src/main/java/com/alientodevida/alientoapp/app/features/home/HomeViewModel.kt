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
import com.alientodevida.alientoapp.domain.youtube.YoutubeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val youtubeRepository: YoutubeRepository,
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
        CategoryItem(
            "Aliento de Vida",
            Constants.CHURCH_IMAGE,
            CategoryItemType.CHURCH
        ),
        CategoryItem(
            "Campus",
            Constants.CAMPUS_IMAGE,
            CategoryItemType.CAMPUSES
        ),
        CategoryItem(
            "Cursos",
            Constants.COURSES_IMAGE,
            CategoryItemType.COURSES
        )
    )

    private val _sermonsItems = MutableLiveData<ViewModelResult<List<CarouselItem>>>()
    val sermonsItems = _sermonsItems

    private val _home = MutableLiveData<ViewModelResult<Home>>()
    val home = _home

    init {
        _sermonsItems.value = ViewModelResult.Success(
            listOf(CategoryItem("Ver Prédicas", Constants.SERMONS_IMAGE, CategoryItemType.SERMONS))
        )
        getHome()
    }

    fun getHome() {
        liveDataResult(_home) {
            val home = homeRepository.getHome()
            preferences.home = home
            getSermonItems(home.youtubePlaylistId)
            home
        }
    }

    private fun getSermonItems(playlistId: String) {
        liveDataResult(_sermonsItems) {
            val sermons =
                youtubeRepository.refreshYoutubePlaylist(playlistId)

            val carouselItems = arrayListOf<CarouselItem>()
            carouselItems += CategoryItem("Ver Prédicas", Constants.SERMONS_IMAGE, CategoryItemType.SERMONS)
            carouselItems += sermons
                .filter { it.thumbnilsUrl != null }
                .map {
                    YoutubeItem(
                        it.name,
                        it.thumbnilsUrl!!.replace("hqdefault.jpg", "maxresdefault.jpg"),
                        it.id
                    )
                }

            carouselItems
        }
    }

}
