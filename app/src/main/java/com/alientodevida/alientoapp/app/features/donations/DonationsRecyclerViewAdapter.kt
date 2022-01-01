package com.alientodevida.alientoapp.app.features.donations

import android.graphics.Color
import android.view.View
import androidx.core.content.ContextCompat
import com.alientodevida.alientoapp.app.BR
import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.app.databinding.ItemPaymentBinding
import com.alientodevida.alientoapp.app.recyclerview.BaseDiffCallback
import com.alientodevida.alientoapp.app.recyclerview.BaseViewHolder
import com.alientodevida.alientoapp.app.utils.Utils
import com.alientodevida.alientoapp.domain.entities.local.PaymentItem

val donationDiffCallback = object : BaseDiffCallback() {
  override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean =
    oldItem is PaymentItem && newItem is PaymentItem && oldItem.id == newItem.id
}

class DonationViewHolder(
  binding: ItemPaymentBinding,
  listener: Listener<PaymentItem>
) : BaseViewHolder<PaymentItem, ItemPaymentBinding>(
  binding,
  BR.item,
  listener,
) {
  
  override fun bind(item: PaymentItem) {
    super.bind(item)
    
    with(binding) {
      this.item = item
      cardConstraintLayout.setOnClickListener {
        listener?.invoke(item, cardConstraintLayout)
      }
      
      when {
        item.paypal != null -> {
          cardConstraintLayout.setBackgroundColor(Color.WHITE)
          
          logo.visibility = View.VISIBLE
          
          noDeCuenta.visibility = View.GONE
          noDeCuentaLabel.visibility = View.GONE
          clabe.visibility = View.GONE
          clabeLabel.visibility = View.GONE
          noDeTarjeta.text = noDeTarjeta.context.getString(R.string.click_to_donate)
          noDeTarjeta.setTextColor(Color.BLACK)
          noDeTarjetaLabel.visibility = View.GONE
        }
        item.bankAccount != null -> {
          val bankAccount = item.bankAccount!!
          
          background.setImageDrawable(
            ContextCompat.getDrawable(
              card.context,
              bankAccount.backgroundResource
            )
          )
          
          logo.visibility = View.GONE
          
          noDeCuenta.text = bankAccount.accountNumber
          clabe.text = bankAccount.clabe
          noDeTarjeta.text = bankAccount.cardNumber
          
          noDeCuenta.setOnClickListener {
            Utils.copyToClipboard(
              context = noDeCuenta.context,
              name = "Número de cuenta",
              value = bankAccount.accountNumber
            )
          }
          clabe.setOnClickListener {
            Utils.copyToClipboard(
              context = clabe.context,
              name = "Clabe",
              value = bankAccount.clabe
            )
          }
          noDeTarjeta.setOnClickListener {
            Utils.copyToClipboard(
              context = noDeTarjeta.context,
              name = "Número de tarjeta",
              value = bankAccount.cardNumber
            )
          }
        }
      }
    }
  }
  
}