package com.alientodevida.alientoapp.ui.prayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alientodevida.alientoapp.data.entities.local.BankAccount
import com.alientodevida.alientoapp.data.entities.local.DonationType
import com.alientodevida.alientoapp.data.entities.local.PaymentItem
import com.alientodevida.alientoapp.data.entities.local.Paypal

class PrayerViewModel : ViewModel() {

    private val _tithesOptions = MutableLiveData<List<PaymentItem>>()
    val tithesOptions: LiveData<List<PaymentItem>>
        get() = _tithesOptions

    private val _offeringsOptions = MutableLiveData<List<PaymentItem>>()
    val offeringsOptions: LiveData<List<PaymentItem>>
        get() = _offeringsOptions
}