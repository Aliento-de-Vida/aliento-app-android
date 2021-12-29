package com.alientodevida.alientoapp.app.features.donations

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alientodevida.alientoapp.app.R
import com.alientodevida.alientoapp.app.base.BaseFragment
import com.alientodevida.alientoapp.app.databinding.FragmentDonationsBinding
import com.alientodevida.alientoapp.app.utils.Utils
import com.alientodevida.alientoapp.domain.entities.local.BankAccount
import com.alientodevida.alientoapp.domain.entities.local.PaymentItem
import com.alientodevida.alientoapp.domain.entities.local.Paypal
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DonationsFragment : BaseFragment<FragmentDonationsBinding>(R.layout.fragment_donations) {
  
  private val viewModel by viewModels<DonationsViewModel>()
  
  private lateinit var tithesRecyclerViewAdapter: DonationsRecyclerViewAdapter
  private lateinit var offeringsRecyclerViewAdapter: DonationsRecyclerViewAdapter
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    
    setUpUi(binding)
  }
  
  private fun setUpUi(binding: FragmentDonationsBinding) {
    with(binding) {
      toolbarView.icBack.setOnClickListener { activity?.onBackPressed() }
      
      setupTithesRecyclerView(tithes)
      setupOfferingsRecyclerView(offerings)
    }
  }
  
  private fun setupTithesRecyclerView(recyclerView: RecyclerView) {
    tithesRecyclerViewAdapter =
      DonationsRecyclerViewAdapter(ItemClick { item -> onCardClick(item) })
    
    viewModel.tithesOptions.observe(viewLifecycleOwner) { result ->
      tithesRecyclerViewAdapter.items = result
      recyclerView.apply {
        layoutManager =
          LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        adapter = tithesRecyclerViewAdapter
      }
    }
  }
  
  private fun setupOfferingsRecyclerView(recyclerView: RecyclerView) {
    offeringsRecyclerViewAdapter =
      DonationsRecyclerViewAdapter(ItemClick { item -> onCardClick(item) })
    
    viewModel.offeringsOptions.observe(viewLifecycleOwner) { result ->
      offeringsRecyclerViewAdapter.items = result
      recyclerView.apply {
        layoutManager =
          LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        adapter = offeringsRecyclerViewAdapter
      }
    }
  }
  
  private fun onCardClick(item: PaymentItem) {
    when (item) {
      is Paypal -> Utils.goToUrl(requireContext(), item.url)
      is BankAccount -> {
        Utils.copyToClipboard(
          context = requireContext(),
          name = "Número de tarjeta",
          value = item.cardNumber
        )
      }
    }
  }
}

























