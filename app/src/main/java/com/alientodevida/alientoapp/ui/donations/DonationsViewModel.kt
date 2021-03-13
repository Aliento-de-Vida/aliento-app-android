package com.alientodevida.alientoapp.ui.donations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alientodevida.alientoapp.R
import com.alientodevida.alientoapp.data.entities.local.BankAccount
import com.alientodevida.alientoapp.data.entities.local.DonationType
import com.alientodevida.alientoapp.data.entities.local.PaymentItem
import com.alientodevida.alientoapp.data.entities.local.Paypal

class DonationsViewModel : ViewModel() {

    private val _tithesOptions = MutableLiveData<List<PaymentItem>>()
    val tithesOptions: LiveData<List<PaymentItem>>
        get() = _tithesOptions

    private val _offeringsOptions = MutableLiveData<List<PaymentItem>>()
    val offeringsOptions: LiveData<List<PaymentItem>>
        get() = _offeringsOptions

    init {
        _tithesOptions.value = arrayListOf(
            BankAccount(
                DonationType.DIEZMO,
                "Abraham Pérez Lara",
                    R.drawable.banamex_card,
                "BANAMEX",
                "5204 1673 5696 4791",
                "428921608",
                "002910428900216088",
            ),
            Paypal(DonationType.DIEZMO,
                "Abraham Pérez Lara",
                "https://www.paypal.com/paypalme/Abrahampl4"
            )
        )

        _offeringsOptions.value = arrayListOf(
            BankAccount(
                DonationType.DIEZMO,
                "Aliento de Vida AC",
                    R.drawable.bbva_card,
                "BBVA BANCOMER",
                "4555 1130 0604 1497",
                "0113500640",
                "012910001135006409",
            ),
            Paypal(DonationType.OFRENDA,
                "Aliento de Vida AC",
                "https://www.paypal.com/paypalme/AlientoDeVidaMx"
            )
        )
    }
}