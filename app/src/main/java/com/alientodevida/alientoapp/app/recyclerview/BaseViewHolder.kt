package com.alientodevida.alientoapp.app.recyclerview

import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.IdRes
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<I : Any, VDB : ViewDataBinding>(
  protected val binding: VDB,
  @IdRes protected val variableId: Int = 0,
  protected val listener: Listener<I>? = null,
) : RecyclerView.ViewHolder(
  binding.root,
), View.OnClickListener {
  
  protected lateinit var item: I
  
  init {
    listener?.let { binding.root.setOnClickListener(this) }
  }
  
  @CallSuper
  open fun bind(item: I) {
    this.item = item
    binding.apply {
      setVariable(variableId, item)
      executePendingBindings()
    }
  }
  
  @CallSuper
  override fun onClick(view: View?) {
    view ?: return
    if (view.id == itemView.id) listener?.invoke(item, view)
  }
  
  fun interface Listener<T : Any> {
    
    operator fun invoke(item: T, view: View)
    
  }
  
  fun interface Factory<T : Any, VDB : ViewDataBinding, out BVH : BaseViewHolder<T, VDB>> {
    
    operator fun invoke(vdb: VDB, listener: Listener<T>?): BVH
    
  }
  
}
