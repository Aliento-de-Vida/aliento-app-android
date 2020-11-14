package com.alientodevida.alientoapp.ui.home

import android.util.Base64
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alientodevida.alientoapp.AppController
import com.alientodevida.alientoapp.data.domain.Repository
import com.alientodevida.alientoapp.data.entities.Token
import kotlinx.coroutines.launch

private const val REUNION_DOMINGOS = "HOME/REUNION_DOMINGOS"
private const val UNO_LA_CONGRE = "HOME/UNO_LA_CONGRE"
private const val GRUPOS_GENERADORES = "HOME/GRUPOS_GENERADORES"
private const val UNO_SOMOS = "HOME/UNO_SOMOS"
private const val PRIMERS = "HOME/PRIMERS"

class HomeViewModel @ViewModelInject constructor(
    private val repository: Repository
): ViewModel() {

    private val _reunionDomingos = MutableLiveData<String>()
    val reunionDomingos: LiveData<String> = _reunionDomingos

    private val _unoLaCongre = MutableLiveData<String>()
    val unoLaCongre: LiveData<String> = _unoLaCongre

    private val _unoSomos = MutableLiveData<String>()
    val unoSomos: LiveData<String> = _unoSomos

    private val _primers = MutableLiveData<String>()
    val primers: LiveData<String> = _primers

    private val _gruposGeneradores = MutableLiveData<String>()
    val gruposGeneradores: LiveData<String> = _gruposGeneradores

    private val _isGettingData = MutableLiveData<Boolean>()
    val isGettingData: LiveData<Boolean> = _isGettingData


    fun getImages() {
        val token = String.format(
            "Basic %s", Base64.encodeToString(
                String.format("%s:%s", "862563945119256", "RsL-A1Z-JkJL0LKQpyj8f2UmkT8").toByteArray(), Base64.DEFAULT
            )
        ).trim()

        viewModelScope.launch {
            _isGettingData.postValue(true)
            val response = repository.getImageUrl(token, REUNION_DOMINGOS)
            _reunionDomingos.postValue(response.resources.first().url)
            _isGettingData.postValue(false)
        }
        viewModelScope.launch {
            _isGettingData.postValue(true)
            val response = repository.getImageUrl(token, UNO_LA_CONGRE)
            _unoLaCongre.postValue(response.resources.first().url)
            _isGettingData.postValue(false)
        }
        viewModelScope.launch {
            _isGettingData.postValue(true)
            val response = repository.getImageUrl(token, GRUPOS_GENERADORES)
            _gruposGeneradores.postValue(response.resources.first().url)
            _isGettingData.postValue(false)
        }
        viewModelScope.launch {
            _isGettingData.postValue(true)
            val response = repository.getImageUrl(token, UNO_SOMOS)
            _unoSomos.postValue(response.resources.first().url)
            _isGettingData.postValue(false)
        }
        viewModelScope.launch {
            _isGettingData.postValue(true)
            val response = repository.getImageUrl(token, PRIMERS)
            _primers.postValue(response.resources.first().url)
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