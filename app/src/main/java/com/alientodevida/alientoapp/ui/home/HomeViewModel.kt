package com.alientodevida.alientoapp.ui.home

import android.util.Base64
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alientodevida.alientoapp.R
import com.alientodevida.alientoapp.data.domain.Repository
import com.alientodevida.alientoapp.data.entities.local.CarrouselItem
import com.alientodevida.alientoapp.data.entities.local.CarrouselItemType
import kotlinx.coroutines.launch
import retrofit2.HttpException

// TODO rename
private const val SERMONS = "HOME/REUNION_DOMINGOS"
private const val DONATIONS = "HOME/UNO_LA_CONGRE"
private const val PRAYER = "HOME/GRUPOS_GENERADORES"
private const val WEB_PAGE = "HOME/UNO_SOMOS"
private const val EBOOK = "HOME/PRIMERS"

class HomeViewModel @ViewModelInject constructor(
    private val repository: Repository
): ViewModel() {

    private val _carouseItems = MutableLiveData<List<CarrouselItem>>()
    val carouseItems: LiveData<List<CarrouselItem>>
        get() = _carouseItems

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
            CarrouselItem(CarrouselItemType.ALIENTO_DE_VIDA, null, R.drawable.carrousel_adv),
            CarrouselItem(CarrouselItemType.MANOS_EXTENDIDAS, null, R.drawable.carrousel_manos_extendidas),
            CarrouselItem(CarrouselItemType.CURSOS, null, R.drawable.carrousel_cursos)
        )
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