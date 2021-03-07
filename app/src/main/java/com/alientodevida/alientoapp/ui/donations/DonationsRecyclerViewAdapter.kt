package com.alientodevida.alientoapp.ui.donations

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.alientodevida.alientoapp.R
import com.alientodevida.alientoapp.data.entities.local.BankAccount
import com.alientodevida.alientoapp.data.entities.local.PaymentItem
import com.alientodevida.alientoapp.data.entities.local.Paypal
import com.alientodevida.alientoapp.databinding.ItemPaymentBinding
import com.alientodevida.alientoapp.utils.Utils
import com.bumptech.glide.Glide

class ItemClick(val block: (PaymentItem) -> Unit) {
    fun onClick(paymentItem: PaymentItem) = block(paymentItem)
}

class PaymentItemViewHolder(val deviceDataBinding: ItemPaymentBinding) :
        RecyclerView.ViewHolder(deviceDataBinding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.item_payment
    }
}

class DonationsRecyclerViewAdapter(private val callback: ItemClick) : RecyclerView.Adapter<PaymentItemViewHolder>() {

    var items: List<PaymentItem> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentItemViewHolder {
        val withDataBinding: ItemPaymentBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.context), PaymentItemViewHolder.LAYOUT, parent, false)
        return PaymentItemViewHolder(withDataBinding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: PaymentItemViewHolder, position: Int) {
        holder.deviceDataBinding.also {
            val item = items[position]

            it.callback = callback
            it.item = item
            it.ownerName.text = item.ownerName

            when (item) {
                is Paypal -> {
                    it.cardConstraintLayout.setBackgroundColor(Color.parseColor("#FFFFFF"))

                    it.bankLogo.visibility = View.GONE
                    it.cardLogo.visibility = View.GONE
                    it.logo.visibility = View.VISIBLE

                    it.noDeCuenta.visibility = View.GONE
                    it.noDeCuentaLabel.visibility = View.GONE
                    it.clabe.visibility = View.GONE
                    it.clabeLabel.visibility = View.GONE
                    it.noDeTarjeta.text = "Click para donar"
                    it.noDeTarjeta.setTextColor(Color.BLACK)
                    it.noDeTarjetaLabel.visibility = View.GONE
                }
                is BankAccount -> {
                    it.cardConstraintLayout.background = ContextCompat.getDrawable(it.card.context, item.gradient)

                    Glide.with(it.bankLogo)
                            .load(item.bankLogo)
                            .into(it.bankLogo)

                    Glide.with(it.cardLogo)
                            .load(item.cardLogo)
                            .into(it.cardLogo)

                    it.logo.visibility = View.GONE

                    it.noDeCuenta.text = item.accountNumber
                    it.clabe.text = item.clabe
                    it.noDeTarjeta.text = item.cardNumber

                    it.noDeCuenta.setOnClickListener {
                        Utils.copyToClipboard(name = "Número de cuenta", value = item.accountNumber)
                    }
                    it.clabe.setOnClickListener {
                        Utils.copyToClipboard(name = "Clabe", value = item.clabe)
                    }
                    it.noDeTarjeta.setOnClickListener {
                        Utils.copyToClipboard(name = "Número de tarjeta", value = item.cardNumber)
                    }
                }
            }

            when (position) {
                0 -> {
                    val params = holder.deviceDataBinding.root.layoutParams as RecyclerView.LayoutParams
                    params.leftMargin = 20
                    params.rightMargin = 0
                    holder.deviceDataBinding.root.layoutParams = params
                }
                items.lastIndex -> {
                    val params = holder.deviceDataBinding.root.layoutParams as RecyclerView.LayoutParams
                    params.rightMargin = 20
                    params.leftMargin = 0
                    holder.deviceDataBinding.root.layoutParams = params
                }
                else -> {
                    val params = holder.deviceDataBinding.root.layoutParams as RecyclerView.LayoutParams
                    params.leftMargin = 0
                    params.rightMargin = 0
                    holder.deviceDataBinding.root.layoutParams = params
                }
            }
        }
    }
}