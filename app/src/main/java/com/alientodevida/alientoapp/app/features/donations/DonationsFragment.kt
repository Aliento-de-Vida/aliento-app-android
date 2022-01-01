package com.alientodevida.alientoapp.app.features.donations

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.app.base.BaseFragment
import com.alientodevida.alientoapp.app.databinding.FragmentDonationsBinding
import com.alientodevida.alientoapp.app.databinding.ItemPaymentBinding
import com.alientodevida.alientoapp.app.recyclerview.BaseDiffAdapter
import com.alientodevida.alientoapp.app.recyclerview.BaseViewHolder
import com.alientodevida.alientoapp.app.utils.Utils
import com.alientodevida.alientoapp.domain.entities.local.PaymentItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DonationsFragment : BaseFragment<FragmentDonationsBinding>(R.layout.fragment_donations) {
  
  private val viewModel by viewModels<DonationsViewModel>()
  
  private val offeringsAdapter = BaseDiffAdapter(donationDiffCallback)
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    
    setUpUi(binding)
  }
  
  private fun setUpUi(binding: FragmentDonationsBinding) {
    with(binding) {
      toolbarView.icBack.setOnClickListener { activity?.onBackPressed() }
      
      setupOfferingsRecyclerView(offerings)
    }
  }
  
  private fun setupOfferingsRecyclerView(recyclerView: RecyclerView) {
    val resourceListener = BaseViewHolder.Listener { item: PaymentItem, _ ->
      onCardClick(item)
    }
    offeringsAdapter.register<PaymentItem, ItemPaymentBinding, DonationViewHolder>(
      R.layout.item_payment,
      resourceListener,
    )
    
    viewModel.offeringsOptions.observe(viewLifecycleOwner) { result ->
      offeringsAdapter.submitList(result)
      recyclerView.apply {
        layoutManager =
          LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adapter = offeringsAdapter
      }
    }
  }
  
  private fun onCardClick(item: PaymentItem) {
    when {
      item.paypal != null -> Utils.goToUrl(requireContext(), item.paypal!!.url)
      item.bankAccount != null -> {
        Utils.copyToClipboard(
          context = requireContext(),
          name = "Número de tarjeta",
          value = item.bankAccount!!.cardNumber
        )
      }
    }
  }
}

























