package com.alientodevida.alientoapp.app.recyclerview

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class BaseAdapter
  : RecyclerView.Adapter<BaseViewHolder<*, *>>() {
  
  val recyclerAdapter = RecyclerAdapter(::getItem)
  private val items = ArrayList<Any>()
  
  override fun getItemCount(): Int = items.size
  
  override fun getItemViewType(position: Int): Int = recyclerAdapter.getItemViewType(position)
  
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*, *> =
    recyclerAdapter.onCreateViewHolder(parent, viewType)
  
  override fun onBindViewHolder(holder: BaseViewHolder<*, *>, position: Int) =
    recyclerAdapter.onBindViewHolder(holder, position)
  
  @Suppress("UNCHECKED_CAST")
  fun <T> getItem(position: Int): T = items[position] as T
  
  fun submitList(list: List<Any>?) = synchronized(this) {
    items.clear()
    list?.let { items.addAll(it) }
    notifyDataSetChanged()
  }
  
  fun <I : Any, VDB : ViewDataBinding> register(
    itemClass: Class<I>,
    @LayoutRes viewType: Int,
    listener: BaseViewHolder.Listener<I>? = null,
    factory: BaseViewHolder.Factory<I, VDB, BaseViewHolder<I, VDB>>,
  ) = recyclerAdapter.register(itemClass, viewType, listener, factory)
  
  inline fun <reified I : Any, reified VDB : ViewDataBinding, reified BVH : BaseViewHolder<I, VDB>> register(
    @LayoutRes viewType: Int,
    listener: BaseViewHolder.Listener<I>? = null,
  ) = recyclerAdapter.register<I, VDB, BVH>(viewType, listener)
  
}
