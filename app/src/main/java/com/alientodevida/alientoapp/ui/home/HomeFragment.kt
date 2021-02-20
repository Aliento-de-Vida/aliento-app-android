package com.alientodevida.alientoapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alientodevida.alientoapp.R
import com.alientodevida.alientoapp.data.entities.local.CarrouselItem
import com.alientodevida.alientoapp.databinding.FragmentHomeBinding
import com.alientodevida.alientoapp.databinding.ItemCarrouselRecyclerViewBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel by viewModels<HomeViewModel>()

    private lateinit var carrouselRecyclerViewAdapter: CarrouselRecyclerViewAdapter
    private lateinit var devicesRecyclerView: RecyclerView

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val binding = FragmentHomeBinding.inflate(layoutInflater)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        setupUI(binding)

        return binding.root
    }

    private fun setupUI(binding: FragmentHomeBinding) {

        binding.swiperefresh.setOnRefreshListener {
            refreshImages()
        }

        devicesRecyclerView = binding.carrousel
        setupCarrousel()

        viewModel.reunionDomingos.observe(viewLifecycleOwner, {
            if (it == null) viewModel.refreshReunionDomingo()
            binding.swiperefresh.isRefreshing = false
        })

        viewModel.unoLaCongre.observe(viewLifecycleOwner, {
            if (it == null) viewModel.refreshUnoLaCongre()
            binding.swiperefresh.isRefreshing = false
        })

        viewModel.unoSomos.observe(viewLifecycleOwner, {
            if (it == null) viewModel.refreshUnoSomos()
            binding.swiperefresh.isRefreshing = false
        })

        viewModel.primers.observe(viewLifecycleOwner, {
            if (it == null) viewModel.refreshPrimers()
            binding.swiperefresh.isRefreshing = false
        })

        viewModel.gruposGeneradores.observe(viewLifecycleOwner, {
            if (it == null) viewModel.refreshGruposGeneradores()
            binding.swiperefresh.isRefreshing = false
        })
    }

    private fun setupCarrousel() {

        viewModel.carrouselItems.observe(viewLifecycleOwner) { result: List<CarrouselItem> ->
            carrouselRecyclerViewAdapter = CarrouselRecyclerViewAdapter(ItemClick { item ->
                // TODO
            })
            carrouselRecyclerViewAdapter.items = result
            devicesRecyclerView.apply {

                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = carrouselRecyclerViewAdapter
            }
        }
    }

    private fun refreshImages() {
        viewModel.refreshReunionDomingo()
        viewModel.refreshUnoLaCongre()
        viewModel.refreshUnoSomos()
        viewModel.refreshPrimers()
        viewModel.refreshGruposGeneradores()
    }
}


class ItemClick(val block: (CarrouselItem) -> Unit) {
    fun onClick(device: CarrouselItem) = block(device)
}

class CarrouselRecyclerViewAdapter(private val callback: ItemClick) : RecyclerView.Adapter<CarrouselItemViewHolder>() {

    var items: List<CarrouselItem> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarrouselItemViewHolder {
        val withDataBinding: ItemCarrouselRecyclerViewBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), CarrouselItemViewHolder.LAYOUT, parent, false)
        return CarrouselItemViewHolder(withDataBinding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: CarrouselItemViewHolder, position: Int) {
        holder.deviceDataBinding.also {
            it.item = items[position]
            it.callback = callback

            if (it.item!!.imageResource != null) {
                Glide.with(holder.deviceDataBinding.imageView.context)
                    .load(it.item!!.imageResource)
                    .centerCrop()
                    .into(holder.deviceDataBinding.imageView)

            } else if (it.item!!.imageUrl != null) {
                Glide.with(holder.deviceDataBinding.imageView.context)
                    .load(it.item!!.imageUrl)
                    .centerCrop()
                    .into(holder.deviceDataBinding.imageView)
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

class CarrouselItemViewHolder(val deviceDataBinding: ItemCarrouselRecyclerViewBinding) :
    RecyclerView.ViewHolder(deviceDataBinding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.item_carrousel_recycler_view
    }
}