package com.alientodevida.alientoapp.app.features.home

import android.app.Application
import androidx.lifecycle.LiveData
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
import com.alientodevida.alientoapp.domain.logger.Logger
import com.alientodevida.alientoapp.domain.preferences.Preferences
import com.alientodevida.alientoapp.domain.youtube.YoutubeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val youtubeRepository: YoutubeRepository,
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
            "Manos Extendidas",
            Constants.SOCIAL_WORK_IMAGE,
            CategoryItemType.SOCIAL_WORK
        ),
        CategoryItem(
            "Cursos",
            Constants.COURSES_IMAGE,
            CategoryItemType.COURSES
        )
    )

    private val _sermonsItems = MutableLiveData<ViewModelResult<List<CarouselItem>>>()
    val sermonsItems: LiveData<ViewModelResult<List<CarouselItem>>>
        get() = _sermonsItems

    init {
        _sermonsItems.value = ViewModelResult.Success(
            listOf(CategoryItem("Prédicas", Constants.SERMONS_IMAGE, CategoryItemType.SERMONS))
        )
        getSermonItems()
    }

    fun getSermonItems() {
        liveDataResult(_sermonsItems) {
            val sermons =
                youtubeRepository.refreshYoutubePlaylist(Constants.YOUTUBE_PREDICAS_PLAYLIST_CODE)

            val carouselItems = arrayListOf<CarouselItem>()
            carouselItems += CategoryItem("", Constants.SERMONS_IMAGE, CategoryItemType.SERMONS)
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
