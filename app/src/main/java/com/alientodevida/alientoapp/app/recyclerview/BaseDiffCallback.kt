package com.alientodevida.alientoapp.app.recyclerview

import androidx.recyclerview.widget.DiffUtil
import java.util.*

abstract class BaseDiffCallback : DiffUtil.ItemCallback<Any>() {
  
  override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean =
    Objects.deepEquals(oldItem, newItem)
  
}
