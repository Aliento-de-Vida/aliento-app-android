package com.alientodevida.alientoapp.app.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
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
		return binding.root
	}
}