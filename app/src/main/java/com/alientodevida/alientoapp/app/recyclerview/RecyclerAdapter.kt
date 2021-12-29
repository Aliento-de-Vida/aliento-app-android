package com.alientodevida.alientoapp.app.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.collection.ArrayMap
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import java.lang.reflect.Constructor

class RecyclerAdapter(
  private val itemRetriever: (Int) -> Any,
) {
  
  private val typeMap = ArrayMap<Class<*>, Int>()
  private val factoryMap =
    ArrayMap<Int, BaseViewHolder.Factory<Any, ViewDataBinding, BaseViewHolder<Any, ViewDataBinding>>>()
  private val listenerMap = ArrayMap<Int, BaseViewHolder.Listener<Any>?>()
  
  fun getItemViewType(position: Int): Int {
    val item: Any = itemRetriever(position)
    val itemClass = item::class.java
    val viewType = typeMap[itemClass]
    return viewType ?: throw RuntimeException("ViewType not found for $itemClass")
  }
  
  @Suppress("UNCHECKED_CAST")
  fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*, *> {
    val factory =
      factoryMap[viewType] ?: throw RuntimeException("Factory not found for $viewType")
    val listener = listenerMap[viewType]
    val inflater = LayoutInflater.from(parent.context)
    val binding = DataBindingUtil.inflate<ViewDataBinding>(inflater, viewType, parent, false)
    return factory(binding, listener)
  }
  
  @Suppress("UNCHECKED_CAST")
  fun onBindViewHolder(holder: BaseViewHolder<*, *>, position: Int) {
    val item: Any = itemRetriever(position)
    (holder as BaseViewHolder<Any, *>).bind(item)
  }
  
  @Suppress("UNCHECKED_CAST")
  fun <I : Any, VDB : ViewDataBinding> register(
    itemClass: Class<I>,
    @LayoutRes viewType: Int,
    listener: BaseViewHolder.Listener<I>? = null,
    factory: BaseViewHolder.Factory<I, VDB, BaseViewHolder<I, VDB>>,
  ) {
    typeMap[itemClass] = viewType
    listenerMap[viewType] = listener as BaseViewHolder.Listener<Any>?
    factoryMap[viewType] =
      factory as BaseViewHolder.Factory<Any, ViewDataBinding, BaseViewHolder<Any, ViewDataBinding>>
  }
  
  inline fun <reified I : Any, reified VDB : ViewDataBinding, reified BVH : BaseViewHolder<I, VDB>> register(
    @LayoutRes viewType: Int,
    listener: BaseViewHolder.Listener<I>? = null,
  ) {
    val constructor: Constructor<BVH> = BVH::class.java.getDeclaredConstructor(
      VDB::class.java,
      BaseViewHolder.Listener::class.java
    )
    val factory = BaseViewHolder.Factory<I, VDB, BVH> { vdb, lstnr ->
      constructor.newInstance(vdb, lstnr)
    }
    register(I::class.java, viewType, listener, factory)
  }
  
}
