package com.alientodevida.alientoapp.app.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetFragment<VDB : ViewDataBinding>(
  @LayoutRes protected val layoutId: Int,
) : BottomSheetDialogFragment() {
  
  protected lateinit var binding: VDB
  
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?,
  ): View {
    binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
    binding.lifecycleOwner = viewLifecycleOwner
    
    setOnlyExpanded()
    return binding.root
  }
  
  private fun setOnlyExpanded() {
    dialog?.setOnShowListener {
      val bottomSheet: View? =
        (it as BottomSheetDialog).findViewById(com.google.android.material.R.id.design_bottom_sheet)
      BottomSheetBehavior.from(bottomSheet!!).state = BottomSheetBehavior.STATE_EXPANDED
      BottomSheetBehavior.from(bottomSheet).skipCollapsed = true
    }
  }
  
}