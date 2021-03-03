package com.alientodevida.alientoapp.ui.home

import android.util.Base64
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.alientodevida.alientoapp.R
import com.alientodevida.alientoapp.data.domain.Repository
import com.alientodevida.alientoapp.data.entities.local.CarouselItem
import com.alientodevida.alientoapp.data.entities.local.CategoryItem
import com.alientodevida.alientoapp.data.entities.local.CategoryItemType
import com.alientodevida.alientoapp.data.entities.local.YoutubeItem
import com.alientodevida.alientoapp.utils.Constants
import kotlinx.coroutines.launch
import retrofit2.HttpException

// TODO rename
private const val SERMONS = "HOME/REUNION_DOMINGOS"
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
        val carouselItems = items.filter { it.thumbnilsUrl != null }
        carouselItems.map {
            it.thumbnilsUrl?.replace("hqdefault.jpg", "maxresdefault.jpg")?.let {url ->
                YoutubeItem(it.name, url, it.id)
            }?: run {
                throw Exception("Playlist item with no image")
            }
        }
    }

    private val _isGettingData = MutableLiveData<Boolean>()
    val isGettingData: LiveData<Boolean> = _isGettingData

    val sermons = repository.getImageUrl(SERMONS)

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
        _carouseItems.value = listOf(
            CategoryItem("Aliento de Vida", CategoryItemType.CHURCH, R.drawable.carrousel_adv),
            CategoryItem("Manos Extendidas", CategoryItemType.MANOS_EXTENDIDAS, R.drawable.carrousel_manos_extendidas),
            CategoryItem("Cursos", CategoryItemType.CURSOS, R.drawable.carrousel_cursos)
        )
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

    fun refreshSermonsImage() {
        viewModelScope.launch {
            _isGettingData.postValue(true)
            try {
                repository.refreshImageUrl(token, SERMONS)
            } catch (ex: HttpException) {
                ex.printStackTrace()
            }
            _isGettingData.postValue(false)
        }
    }
    fun refreshDonationsImage() {
        viewModelScope.launch {
            _isGettingData.postValue(true)
            try {
                repository.refreshImageUrl(token, DONATIONS)
            } catch (ex: HttpException) {
                ex.printStackTrace()
            }
            _isGettingData.postValue(false)
        }
    }
    fun refreshPrayerImage() {
        viewModelScope.launch {
            _isGettingData.postValue(true)
            try {
                repository.refreshImageUrl(token, PRAYER)
            } catch (ex: HttpException) {
                ex.printStackTrace()
            }
            _isGettingData.postValue(false)
        }
    }
    fun refreshWebPageImage() {
        viewModelScope.launch {
            _isGettingData.postValue(true)
            try {
                repository.refreshImageUrl(token, WEB_PAGE)
            } catch (ex: HttpException) {
                ex.printStackTrace()
            }
            _isGettingData.postValue(false)
        }
    }
    fun refreshEbookImage() {
        viewModelScope.launch {
            _isGettingData.postValue(true)
            try {
                repository.refreshImageUrl(token, EBOOK)
            } catch (ex: HttpException) {
                ex.printStackTrace()
            }
            _isGettingData.postValue(false)
        }
    }

    /*private fun getReunionDomingos() {
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val myRef: DatabaseReference =
            database.getReference(this.getString(R.string.realtime_database_reunion_domingo))

        // Read from the database
        myRef.addListenerForSingleValueEvent(object : ValueEventListener() {
            fun onDataChange(dataSnapshot: DataSnapshot) {
                MainActivity.reunionDomingosObject =
                    dataSnapshot.getValue(ReunionDomingos::class.java)
            }

            fun onCancelled(databaseError: DatabaseError) {}
        })
    }*/
}