package com.alientodevida.alientoapp.ui.home

import android.os.Parcel
import android.os.Parcelable
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel @ViewModelInject constructor(
    
): ViewModel(), Parcelable {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HomeViewModel> {
        override fun createFromParcel(parcel: Parcel): HomeViewModel {
            return HomeViewModel(parcel)
        }

        override fun newArray(size: Int): Array<HomeViewModel?> {
            return arrayOfNulls(size)
        }
    }
}