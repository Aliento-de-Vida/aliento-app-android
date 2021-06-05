package com.alientodevida.alientoapp.ui.donations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alientodevida.alientoapp.data.entities.local.BankAccount
import com.alientodevida.alientoapp.data.entities.local.PaymentItem
import com.alientodevida.alientoapp.data.entities.local.Paypal
import com.alientodevida.alientoapp.databinding.FragmentDonationsBinding
import com.alientodevida.alientoapp.utils.Utils

class DonationsFragment : Fragment() {

    private val viewModel by viewModels<DonationsViewModel>()

    private lateinit var tithesRecyclerViewAdapter: DonationsRecyclerViewAdapter
    private lateinit var offeringsRecyclerViewAdapter: DonationsRecyclerViewAdapter

    companion object {
        fun newInstance() = DonationsFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentDonationsBinding.inflate(layoutInflater)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        setupTithesRecyclerView(binding.tithes)
        setupOfferingsRecyclerView(binding.offerings)

        return binding.root
    }

    private fun setupTithesRecyclerView(recyclerView: RecyclerView) {
        tithesRecyclerViewAdapter = DonationsRecyclerViewAdapter(ItemClick { item -> onCardClick(item) })

        viewModel.tithesOptions.observe(viewLifecycleOwner) { result ->
            tithesRecyclerViewAdapter.items = result
            recyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = tithesRecyclerViewAdapter
            }
        }
    }

    private fun setupOfferingsRecyclerView(recyclerView: RecyclerView) {
        offeringsRecyclerViewAdapter = DonationsRecyclerViewAdapter(ItemClick { item -> onCardClick(item) })

        viewModel.offeringsOptions.observe(viewLifecycleOwner) { result ->
            offeringsRecyclerViewAdapter.items = result
            recyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = offeringsRecyclerViewAdapter
            }
        }
    }

    private fun onCardClick(item: PaymentItem) {
        when (item) {
            is Paypal -> Utils.goToUrl(requireContext(), item.url)
            is BankAccount -> { Utils.copyToClipboard(name = "Número de tarjeta", value = item.cardNumber) }
        }
    }
}
























