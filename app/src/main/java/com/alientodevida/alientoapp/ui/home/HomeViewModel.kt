package com.alientodevida.alientoapp.ui.home

import android.util.Base64
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.alientodevida.alientoapp.AppController
import com.alientodevida.alientoapp.R
import com.alientodevida.alientoapp.data.domain.Repository
import com.alientodevida.alientoapp.data.entities.local.CarouselItem
import com.alientodevida.alientoapp.data.entities.local.CategoryItem
import com.alientodevida.alientoapp.data.entities.local.CategoryItemType
import com.alientodevida.alientoapp.data.entities.local.YoutubeItem
import com.alientodevida.alientoapp.data.entities.network.CsrfToken
import com.alientodevida.alientoapp.data.entities.network.Token
import com.alientodevida.alientoapp.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import retrofit2.HttpException

// TODO rename
private const val SERMONS = "adv/PREDICAS"
private const val CHURCH = "adv/ALIENTO_DE_VIDA"
private const val SOCIAL_WORK = "adv/MANOS_EXTENDIDAS"
private const val COURSES = "adv/CURSOS"

private const val DONATIONS = "adv/DONACIONES"
private const val PRAYER = "adv/ORACION"
private const val WEB_PAGE = "adv/PAGINAWEB"
private const val EBOOK = "adv/EBOOK"

class HomeViewModel @ViewModelInject constructor(
    private val repository: Repository
): ViewModel() {

    private val _carouseItems = MutableLiveData<List<CategoryItem>>()
    val carouseItems: LiveData<List<CategoryItem>>
        get() = _carouseItems

    private val sermonsItems = repository.getYoutubePlaylist()
    val sermonsItemsTransformation: LiveData<List<CarouselItem>> = Transformations.map(sermonsItems) { items ->
        val carouselItems = arrayListOf<CarouselItem>(CategoryItem("Prédicas", "", CategoryItemType.SERMONS))

        carouselItems.addAll(
                items.filter { it.thumbnilsUrl != null }
                        .map {
                            it.thumbnilsUrl?.replace("hqdefault.jpg", "maxresdefault.jpg")?.let {url ->
                                YoutubeItem(it.name, url, it.id)
                            }?: run {
                                throw Exception("Playlist item with no image")
                            }
                        }
        )

        carouselItems
    }

    private val _isGettingData = MutableLiveData<Boolean>()
    val isGettingData: LiveData<Boolean> = _isGettingData

    val sermons = repository.getImageUrl(SERMONS)
    val church = repository.getImageUrl(CHURCH)
    val socialWork = repository.getImageUrl(SOCIAL_WORK)
    val courses = repository.getImageUrl(COURSES)

    val donations = repository.getImageUrl(DONATIONS)
    val prayer = repository.getImageUrl(PRAYER)
    val webPage = repository.getImageUrl(WEB_PAGE)
    val ebook = repository.getImageUrl(EBOOK)

    private val token = String.format(
            "Basic %s", Base64.encodeToString(
            String.format("%s:%s", "862563945119256", "RsL-A1Z-JkJL0LKQpyj8f2UmkT8").toByteArray(), Base64.DEFAULT
    )
    ).trim()

    init {
        getCsrfToken()

        // TODO: 07/03/21 each livedata object gets refreshed 5 times for each image update
        refreshImages(false)

        _carouseItems.value = listOf(
                CategoryItem("Aliento de Vida", null, CategoryItemType.CHURCH),
                CategoryItem("Manos Extendidas", null, CategoryItemType.MANOS_EXTENDIDAS),
                CategoryItem("Cursos", null, CategoryItemType.CURSOS)
        )
    }

    private fun getCsrfToken(isExpired: Boolean = true) {
        var token = AppController.get<CsrfToken>(CsrfToken.key)
        if (token == null || isExpired) {
            viewModelScope.launch {
                token = repository.getCsrfToken()
                AppController.save(token!!, CsrfToken.key)
            }
        }
    }

    fun refreshContent() {
        _isGettingData.postValue(true)
        viewModelScope.launch {
            try {
                repository.refreshYoutubePlaylist(Constants.YOUTUBE_DEVELOPER_KEY, Constants.YOUTUBE_PREDICAS_PLAYLIST_CODE)
                _isGettingData.postValue(false)

            } catch (ex: HttpException) {
                _isGettingData.postValue(false)
                ex.printStackTrace()
            }
        }
    }

    fun refreshImages(isForceRefresh: Boolean = true) {
        viewModelScope.launch {
            _isGettingData.postValue(true)
            try {
                if (isForceRefresh) {
                    refreshAllImages()

                } else {
                    sermons.observeOnce(Observer { if (it == null) refreshImages() }) }

            } catch (ex: HttpException) {
                ex.printStackTrace()
            }
            _isGettingData.postValue(false)
        }
    }

    private suspend fun refreshAllImages() {
        repository.refreshImageUrl(token, SERMONS)
        val churchImage = repository.refreshImageUrl(token, CHURCH)
        val socialWorkImage = repository.refreshImageUrl(token, SOCIAL_WORK)
        val coursesImage = repository.refreshImageUrl(token, COURSES)

        _carouseItems.value = listOf(
                CategoryItem("Aliento de Vida", churchImage.imageUrl.replace("http", "https"), CategoryItemType.CHURCH),
                CategoryItem("Manos Extendidas", socialWorkImage.imageUrl.replace("http", "https"), CategoryItemType.MANOS_EXTENDIDAS),
                CategoryItem("Cursos", coursesImage.imageUrl.replace("http", "https"), CategoryItemType.CURSOS)
        )

        repository.refreshImageUrl(token, DONATIONS)
        repository.refreshImageUrl(token, PRAYER)
        repository.refreshImageUrl(token, WEB_PAGE)
        repository.refreshImageUrl(token, EBOOK)

    }
}


fun <T> LiveData<T>.observeOnce(observer: Observer<T>) {
    observeForever(object : Observer<T> {
        override fun onChanged(t: T?) {
            observer.onChanged(t)
            removeObserver(this)
        }
    })
}