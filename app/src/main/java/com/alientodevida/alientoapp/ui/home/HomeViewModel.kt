package com.alientodevida.alientoapp.ui.home

import android.util.Base64
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.alientodevida.alientoapp.AppController
import com.alientodevida.alientoapp.data.domain.Repository
import com.alientodevida.alientoapp.data.entities.local.*
import com.alientodevida.alientoapp.data.entities.network.CsrfToken
import com.alientodevida.alientoapp.utils.Constants
import kotlinx.coroutines.*
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

    private val _sermonsItems = MutableLiveData<List<CarouselItem>>()
    val sermonsItems: LiveData<List<CarouselItem>>
        get() = _sermonsItems

    private val _isGettingData = MutableLiveData<Boolean>()
    val isGettingData: LiveData<Boolean> = _isGettingData

    val donations = repository.getImageUrlLiveData(DONATIONS)
    val prayer = repository.getImageUrlLiveData(PRAYER)
    val webPage = repository.getImageUrlLiveData(WEB_PAGE)
    val ebook = repository.getImageUrlLiveData(EBOOK)

    private val token = String.format(
        "Basic %s", Base64.encodeToString(
            String.format("%s:%s", "862563945119256", "RsL-A1Z-JkJL0LKQpyj8f2UmkT8").toByteArray(),
            Base64.DEFAULT
        )
    ).trim()

    init {

        getCsrfToken()

        refreshSermonItems(false)
        refreshCategoriesCarousel(false)
        refreshQuickLinks(false)

        _carouseItems.value = listOf(
            CategoryItem("Aliento de Vida", null, CategoryItemType.CHURCH),
            CategoryItem("Manos Extendidas", null, CategoryItemType.MANOS_EXTENDIDAS),
            CategoryItem("Cursos", null, CategoryItemType.CURSOS)
        )

        _sermonsItems.value =
            arrayListOf<CarouselItem>(CategoryItem("Prédicas", null, CategoryItemType.SERMONS))
    }

    /**
     * CSRF Token
     */
    private fun getCsrfToken(isExpired: Boolean = true) {
        var token = AppController.get<CsrfToken>(CsrfToken.key)
        if (token == null || isExpired) {
            viewModelScope.launch {
                token = repository.getCsrfToken()
                AppController.save(token!!, CsrfToken.key)
            }
        }
    }

    /**
     * Sermon items
     */
    fun refreshSermonItems(isForceRefresh: Boolean = true) {
        _isGettingData.postValue(true)
        viewModelScope.launch {
            try {
                if (isForceRefresh) {
                    val sermonsImage = async(Dispatchers.Default) {
                        repository.refreshImageUrl(token, SERMONS)
                    }

                    val sermons = async(Dispatchers.Default) {
                        repository.refreshYoutubePlaylist(
                            Constants.YOUTUBE_DEVELOPER_KEY,
                            Constants.YOUTUBE_PREDICAS_PLAYLIST_CODE
                        )
                    }

                    val sermonItems = createSermonItems(sermonsImage.await().imageUrl, sermons.await())
                    _sermonsItems.value = sermonItems

                } else {
                    repository.getImageUrlLiveData(SERMONS).observeOnce {
                        if (it == null) refreshSermonItems(true)
                        else getCachedSermonItems()
                    }
                }
                _isGettingData.postValue(false)

            } catch (ex: HttpException) {
                _isGettingData.postValue(false)
                ex.printStackTrace()
            }
        }
    }
    private fun getCachedSermonItems() {
        viewModelScope.launch {
            val sermonsImage = async(Dispatchers.IO) {
                repository.getImageUrl(SERMONS)
            }

            val sermons = async(Dispatchers.IO) {
                repository.getYoutubePlaylist()
            }

            val sermonItems = createSermonItems(sermonsImage.await()?.imageUrl, sermons.await())
            _sermonsItems.value = sermonItems
        }
    }
    private fun createSermonItems(sermonsImage: String?, sermons: List<YoutubePlaylistItemEntity>): List<CarouselItem> {
        val carouselItems = arrayListOf<CarouselItem>(
            CategoryItem(
                "",
                sermonsImage?.replace("http", "https"),
                CategoryItemType.SERMONS
            )
        )

        carouselItems.addAll(
            sermons.filter { it.thumbnilsUrl != null }
                .map {
                    it.thumbnilsUrl?.replace("hqdefault.jpg", "maxresdefault.jpg")?.let { url ->
                        YoutubeItem(it.name, url, it.id)
                    } ?: run {
                        throw Exception("Playlist item with no image")
                    }
                }
        )

        return carouselItems
    }

    /**
     * Categories carousel
     */
    fun refreshCategoriesCarousel(isForceRefresh: Boolean = true) {
        _isGettingData.postValue(true)
        viewModelScope.launch {
            try {
                if (isForceRefresh) {
                    val churchImage = async(Dispatchers.Default) {
                        repository.refreshImageUrl(token, CHURCH)
                    }
                    val socialWorkImage = async(Dispatchers.Default) {
                        repository.refreshImageUrl(token, SOCIAL_WORK)
                    }
                    val coursesImage = async(Dispatchers.Default) {
                        repository.refreshImageUrl(token, COURSES)
                    }

                    val categoriesItems = createCategoriesCarouselItems(
                        churchImage.await().imageUrl,
                        socialWorkImage.await().imageUrl,
                        coursesImage.await().imageUrl
                    )
                    _carouseItems.value = categoriesItems

                } else {
                    repository.getImageUrlLiveData(CHURCH).observeOnce {
                        if (it == null) refreshCategoriesCarousel(true)
                        else getCachedCategoriesCarousel()
                    }
                }

            } catch (ex: HttpException) {
                ex.printStackTrace()
            }
            _isGettingData.postValue(false)
        }
    }
    private fun getCachedCategoriesCarousel() {
        viewModelScope.launch {
            val churchImage = async(Dispatchers.Default) {
                repository.getImageUrl(CHURCH)
            }
            val socialWorkImage = async(Dispatchers.Default) {
                repository.getImageUrl(SOCIAL_WORK)
            }
            val coursesImage = async(Dispatchers.Default) {
                repository.getImageUrl(COURSES)
            }

            val categoriesItems = createCategoriesCarouselItems(
                churchImage.await()?.imageUrl,
                socialWorkImage.await()?.imageUrl,
                coursesImage.await()?.imageUrl
            )
            _carouseItems.value = categoriesItems
        }
    }
    private fun createCategoriesCarouselItems(
        churchImage: String?,
        socialWorkImage: String?,
        coursesImage: String?,
    ): List<CategoryItem> {
        return listOf(
            CategoryItem(
                "Aliento de Vida",
                churchImage?.replace("http", "https"),
                CategoryItemType.CHURCH
            ),
            CategoryItem(
                "Manos Extendidas",
                socialWorkImage?.replace("http", "https"),
                CategoryItemType.MANOS_EXTENDIDAS
            ),
            CategoryItem(
                "Cursos",
                coursesImage?.replace("http", "https"),
                CategoryItemType.CURSOS
            )
        )
    }


    /**
     * Quick links
     */
    fun refreshQuickLinks(isForceRefresh: Boolean = true) {
        viewModelScope.launch {
            _isGettingData.postValue(true)
            try {
                if (isForceRefresh) {
                    repository.refreshImageUrl(token, DONATIONS)
                    repository.refreshImageUrl(token, PRAYER)
                    repository.refreshImageUrl(token, WEB_PAGE)
                    repository.refreshImageUrl(token, EBOOK)

                } else {
                    donations.observeOnce {
                        if (it == null)
                            refreshQuickLinks(true)
                    }
                }

            } catch (ex: HttpException) {
                ex.printStackTrace()
            }
            _isGettingData.postValue(false)
        }
    }
}


private fun <T> LiveData<T>.observeOnce(observer: Observer<T>) {
    observeForever(object : Observer<T> {
        override fun onChanged(t: T?) {
            observer.onChanged(t)
            removeObserver(this)
        }
    })
}