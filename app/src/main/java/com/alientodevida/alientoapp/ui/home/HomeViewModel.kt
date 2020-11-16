package com.alientodevida.alientoapp.ui.home

import android.util.Base64
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alientodevida.alientoapp.data.domain.Repository
import kotlinx.coroutines.launch
import retrofit2.HttpException

private const val REUNION_DOMINGOS = "HOME/REUNION_DOMINGOS"
private const val UNO_LA_CONGRE = "HOME/UNO_LA_CONGRE"
private const val GRUPOS_GENERADORES = "HOME/GRUPOS_GENERADORES"
private const val UNO_SOMOS = "HOME/UNO_SOMOS"
private const val PRIMERS = "HOME/PRIMERS"

class HomeViewModel @ViewModelInject constructor(
    private val repository: Repository
): ViewModel() {

    val reunionDomingos = repository.getImageUrl(REUNION_DOMINGOS)
    val unoLaCongre = repository.getImageUrl(UNO_LA_CONGRE)
    val unoSomos = repository.getImageUrl(UNO_SOMOS)
    val primers = repository.getImageUrl(PRIMERS)
    val gruposGeneradores = repository.getImageUrl(GRUPOS_GENERADORES)

    private val _isGettingData = MutableLiveData<Boolean>()
    val isGettingData: LiveData<Boolean> = _isGettingData

    private val token = String.format(
        "Basic %s", Base64.encodeToString(
            String.format("%s:%s", "862563945119256", "RsL-A1Z-JkJL0LKQpyj8f2UmkT8").toByteArray(), Base64.DEFAULT
        )
    ).trim()

    fun refreshReunionDomingo() {
        viewModelScope.launch {
            _isGettingData.postValue(true)
            try {
                repository.refreshImageUrl(token, REUNION_DOMINGOS)
            } catch (ex: HttpException) {
                ex.printStackTrace()
            }
            _isGettingData.postValue(false)
        }
    }
    fun refreshUnoLaCongre() {
        viewModelScope.launch {
            _isGettingData.postValue(true)
            try {

            } catch (ex: HttpException) {
                ex.printStackTrace()
            }
            repository.refreshImageUrl(token, UNO_LA_CONGRE)
            _isGettingData.postValue(false)
        }
    }
    fun refreshGruposGeneradores() {
        viewModelScope.launch {
            _isGettingData.postValue(true)
            try {
                repository.refreshImageUrl(token, GRUPOS_GENERADORES)
            } catch (ex: HttpException) {
                ex.printStackTrace()
            }
            _isGettingData.postValue(false)
        }
    }
    fun refreshUnoSomos() {
        viewModelScope.launch {
            _isGettingData.postValue(true)
            try {
                repository.refreshImageUrl(token, UNO_SOMOS)
            } catch (ex: HttpException) {
                ex.printStackTrace()
            }
            _isGettingData.postValue(false)
        }
    }
    fun refreshPrimers() {
        viewModelScope.launch {
            _isGettingData.postValue(true)
            try {
                repository.refreshImageUrl(token, PRIMERS)
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